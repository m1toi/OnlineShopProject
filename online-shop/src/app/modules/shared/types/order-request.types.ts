export interface OrderRequest {
    orderTimestamp: Date;
    addressId: number;
    locationId: number;
    products: {
      productId: number;
      quantity: number;
    }[];
}
  