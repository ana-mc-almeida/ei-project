# Notes <!-- omit in toc -->

<details>
<summary>Table of Contents</summary>

- [Microservices to Implement](#microservices-to-implement)
  - [Database](#database)
- [Questions](#questions)
  - [Selled Product Topics](#selled-product-topics)

</details>

## Microservices to Implement

- [ ] Purchase
  - CR
  - Find by
  - Find all
  - id | DateTime | preço | produto | supplier | shop | loyalty card |
  - (DateTime,Price,Product,Supplier,shopname,loyaltycardid) -> id autoincrement
- [ ] Customer
  - CRUD
  - Find by
  - Find all
  - id | fiscalNumber | location (Address | PostalCode) | name 
- [ ] Shop 
  - CRUD
  - Find by
  - Find all
  - id | location (Address | PostalCode) | name 
- [ ] Loyalty Card
  - CRUD
  - ID | IdClient | IdsShops
- [ ] Discount Cupon
  - Create
  - Find by
  - Update (?) -> faz sentido se tivermos info de se foi usado
  - 1 kafka topic e manda-se tudo lá para dentro
  - id | discount (produto |  (% ou €)) | expirationDate | loyaltyCardId | IdsShops
- [ ] Cross Selling Recommendation
  - CRUD
  - 1 kafka topic e manda-se tudo lá para dentro
  - id | loyaltyCardId | IdsShops
- [ ] Selled Product
  - 1 kafka topic e manda-se tudo lá para dentro
  - SelledProductByCoupon
  - SelledProductByCustomer
  - SelledProductByShop
  - SelledProductByLocation
  - SelledProductByLoyaltyCard
  - Outros: data da compra, preço, desconto, etc
- [ ] Discount Coupon Analysis

### Database

- LocationType
  - Address | PostalCode 

## Questions

- [ ] Loyalty Card decommission
  - Desassociar só de uma loja ou de todas?
  - ID | IdClient | IdsShops
  - int | int | string JSON
  - Como fazer com os pontos? Aqui ou no client?
- [ ] CRUD para tudo faz sentido?
- [ ] O que é que se manda para o topic de selled product?
- [ ] Discount Coupon analysis using Artificial Intelligence
  - Para quê que é usada a IA se é só uma análise quantitativa?
  - Não seria para emitir um cupão?
- [ ] Ligação DiscountCoupon e Purchase
  - Existe? Tem de existir por causa do Discount Coupon Analysis
  - Pode-se aplicar mais do que um cupão na mesma compra?

- [ ] cross selling recommendation
  - só duas lojas ou podem ser mais?
- [ ] LocationType não pode ser tabela externa para não ter dependencias entre microserviços

### Selled Product Topics

SelledProduct
- location=lisboa .....
- locarion=faro .....
- customer=123 .....

```json
{
    search: 'SelledProductByCustomer',
    customerId: 123,
    dateOfAnalysis: '2023-10-01',
    numberOfResults: 10,
    results: [
        {
            id: 1,
            data: '2023-10-01',
            price: 10.0,
            product: 'Product A',
            supplier: 'Supplier A',
            shop: 'Shop A',
            loyaltyCard: 'Loyalty Card A'
        },
        {
            id: 2,
            data: '2023-10-02',
            price: 20.0,
            product: 'Product B',
            supplier: 'Supplier B',
            shop: 'Shop B',
            loyaltyCard: 'Loyalty Card B'
        }
    ]
},
{
    search: 'SelledProductByCoupon',
    couponId: 123,
    dateOfAnalysis: '2023-10-01',
    numberOfResults: 10,
    results: [
        {
            id: 1,
            data: '2023-10-01',
            price: 10.0,
            product: 'Product A',
            supplier: 'Supplier A',
            shop: 'Shop A',
            loyaltyCard: 'Loyalty Card A'
        },
        {
            id: 2,
            data: '2023-10-02',
            price: 20.0,
            product: 'Product B',
            supplier: 'Supplier B',
            shop: 'Shop B',
            loyaltyCard: 'Loyalty Card B'
        }
    ]
},
{
    search: 'SelledProductByLocation',
    location: 'Lisboa',
    numberOfResults: 10,
},
{
    search: 'SelledProductByLocation',
    location: 'Faro',
    numberOfResults: 5,
}
```