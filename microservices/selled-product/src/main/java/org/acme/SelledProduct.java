package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

public class SelledProduct {

	public Long id;
	public Long idCustomer;
	public Long idLoyaltyCard;
	public Long idDiscountCupon;
	public Long idShop;
	public Long idPurchase;
	public String location;

	public SelledProduct() {
	}

	public SelledProduct(Long id, Long idLoyaltyCard, Long idCustomer, Long idDiscountCupon, Long idShop,
			Long idPurchase, String location) {
		this.id = id;
		this.idLoyaltyCard = idLoyaltyCard;
		this.idCustomer = idCustomer;
		this.idDiscountCupon = idDiscountCupon;
		this.idShop = idShop;
		this.idPurchase = idPurchase;
		this.location = location;
	}

	@Override
	public String toString() {
		return "{id=" + id + ", idLoyaltyCard=" + idLoyaltyCard + ", idCustomer=" + idCustomer + ", idDiscountCupon="
				+ (idDiscountCupon != null ? idDiscountCupon : "null")
				+ ", idShop=" + idShop + ", idPurchase=" + idPurchase
				+ ", location='" + location + '\'' + '}';
	}

	private static SelledProduct from(Row row) {
		return new SelledProduct(
				row.getLong("id"),
				row.getLong("idLoyaltyCard"),
				row.getLong("idCustomer"),
				row.get(Long.class, "idDiscountCupon"),
				row.getLong("idShop"),
				row.getLong("idPurchase"),
				row.getString("location"));
	}

	public static Multi<SelledProduct> findAll(MySQLPool client) {
		return client.query(
				"SELECT id, idLoyaltyCard, idCustomer, idDiscountCupon, idShop, idPurchase, location FROM SelledProduct ORDER BY id ASC")
				.execute()
				.onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
				.onItem().transform(SelledProduct::from);
	}

	public static Uni<SelledProduct> findById(MySQLPool client, Long id) {
		return client.preparedQuery(
				"SELECT id, idLoyaltyCard, idCustomer, idDiscountCupon, idShop, idPurchase, location FROM SelledProduct WHERE id = ?")
				.execute(Tuple.of(id))
				.onItem().transform(RowSet::iterator)
				.onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
	}

	public Uni<Boolean> save(MySQLPool client, Long idLoyaltyCard, Long idCustomer,
			Long idDiscountCupon, Long idShop, Long idPurchase, String location) {
		return client.preparedQuery(
				"INSERT INTO SelledProduct (idLoyaltyCard, idCustomer, idDiscountCupon, idShop, idPurchase, location) VALUES (?, ?, ?, ?, ?, ?)")
				.execute(Tuple.of(idLoyaltyCard, idCustomer, idDiscountCupon != null ? idDiscountCupon : null, idShop,
						idPurchase, location))
				.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> delete(MySQLPool client, Long id_R) {
		return client.preparedQuery("DELETE FROM SelledProduct WHERE id = ?").execute(Tuple.of(id_R))
				.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}
}
