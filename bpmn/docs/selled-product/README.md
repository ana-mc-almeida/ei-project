# Selled Product Analytics Business Process <!-- omit in toc -->

![selledProductAnalytics](assets/SelledProductAnalytics.png)

<details>
<summary>Table of Contents</summary>

- [LAAS - Initiator: Decide Analysis](#laas---initiator-decide-analysis)
- [LAAS - Initiator: Selled Product Analises was emitted](#laas---initiator-selled-product-analises-was-emitted)

</details>

## LAAS - Initiator: Decide Analysis

The first task to analyze selled products, it is necessary to provide the following information:

- **typeOfAnalysis**: The type of analysis to be performed, a string value that can be:
  - DISCOUNT_COUPON
  - LOYALTY_CARD
  - POSTAL_CODE
  - SHOP
- **identifierOfAnalysisType**: The identifier of the analysis type, a string value that can be:
  - For DISCOUNT_COUPON: the ID of the discount coupon.
  - For LOYALTY_CARD: the ID of the loyalty card.
  - For POSTAL_CODE: the postal code.
  - For SHOP: the ID of the shop.

## LAAS - Initiator: Selled Product Analises was emitted

After the selled product analysis is emitted, the initiator must acknowledge that you have received confirmation of the emission.

The following information is provided:

- **purchasesAnalytics**: The analysis of the selled products.
