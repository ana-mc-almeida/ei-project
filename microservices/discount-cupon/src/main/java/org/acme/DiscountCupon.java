package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

public class DiscountCupon {

	public Long id;
	public Long idLoyaltyCard;
	public Long[] idsShops;
	public int discount;
	public java.time.LocalDateTime expirationDate;

	public DiscountCupon() {
	}

	public DiscountCupon(Long id, Long idLoyaltyCard, Long[] idsShops, int discount,
			java.time.LocalDateTime expirationDate) {
		this.id = id;
		this.idLoyaltyCard = idLoyaltyCard;
		this.idsShops = idsShops;
		this.discount = discount;
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return "{id=" + id + ", idLoyaltyCard=" + idLoyaltyCard + ", idsShops=" + idsShops + ", discount=" + discount
				+ ", expirationDate=" + expirationDate + "}";
	}

	private static DiscountCupon from(Row row) {

		Long[] idsShops = row.getString("idsShops") != null
				? java.util.Arrays.stream(row.getString("idsShops").split(","))
						.map(Long::valueOf)
						.toArray(Long[]::new) // Convert to array of Long
				: new Long[0];

		return new DiscountCupon(
				row.getLong("id"),
				row.getLong("idLoyaltyCard"),
				idsShops,
				row.getInteger("discount"),
				row.getLocalDateTime("expirationDate"));
	}

	public static Multi<DiscountCupon> findAll(MySQLPool client) {
		return client.query(
				"SELECT dc.id, dc.idLoyaltyCard, dc.discount, dc.expirationDate, IFNULL(GROUP_CONCAT(dcs.idShop), '') AS idsShops  FROM DiscountCupon dc LEFT JOIN DiscountCuponShops dcs ON dc.id = dcs.idDiscountCupon GROUP BY dc.id ORDER BY dc.id ASC;")
				.execute()
				.onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
				.onItem().transform(DiscountCupon::from);
	}

	public static Uni<DiscountCupon> findById(MySQLPool client, Long id) {
		return client.preparedQuery(
				"SELECT dc.id, dc.idLoyaltyCard, dc.discount, dc.expirationDate, IFNULL(GROUP_CONCAT(dcs.idShop), '') AS idsShops  FROM DiscountCupon dc LEFT JOIN DiscountCuponShops dcs ON dc.id = dcs.idDiscountCupon GROUP BY dc.id WHERE dc.id = ?;")
				.execute(Tuple.of(id))
				.onItem().transform(RowSet::iterator)
				.onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
	}

	// tested and works
	public Uni<Boolean> save(MySQLPool client, Long idLoyaltyCard, Long[] idsShops, int discount,
			java.time.LocalDateTime expirationDate) {
		return client.preparedQuery(
				"INSERT INTO DiscountCupon(idLoyaltyCard, discount, expirationDate) VALUES (?,?,?)")
				.execute(Tuple.of(idLoyaltyCard, discount, expirationDate))
				.onItem().transformToUni(pgRowSet -> {
					if (pgRowSet.rowCount() == 1) {
						return client.query("SELECT LAST_INSERT_ID() AS id")
								.execute()
								.onItem().transform(rowSet -> rowSet.iterator().next().getLong("id"))
								.onItem().transformToUni(discountCuponId -> Multi.createFrom().items(idsShops)
										.onItem().transformToUniAndMerge(idShop -> client.preparedQuery(
												"INSERT INTO DiscountCuponShops(idDiscountCupon, idShop) VALUES (?, ?)")
												.execute(Tuple.of(discountCuponId, idShop)))
										.collect().asList()
										.onItem().transform(insertResults -> true));
					} else {
						return Uni.createFrom().item(false);
					}
				});
	}

	// tested and works
	public static Uni<Boolean> delete(MySQLPool client, Long id_R) {
		return client.preparedQuery("DELETE FROM DiscountCuponShops WHERE idDiscountCupon = ?")
				.execute(Tuple.of(id_R))
				.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1)
				.onItem().transformToUni(deleted -> {
					if (deleted) {
						return client.preparedQuery("DELETE FROM DiscountCupon WHERE id = ?")
								.execute(Tuple.of(id_R))
								.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
					} else {
						return Uni.createFrom().item(false);
					}
				});
	}
}
