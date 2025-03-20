import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { CartProduct } from '../modules/shared/types/cart-product.types';
import { OrderRequest } from '../modules/shared/types/order-request.types';
import { Product } from '../modules/shared/types/products.types';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private readonly CART_KEY = 'shoppingCart';

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getCartItems(): CartProduct[] {
    const cart = localStorage.getItem(this.CART_KEY);
    return cart ? JSON.parse(cart) : [];
  }

  addToCart(product: Product): void {  
    const uniqueId = product.id; 
  
    if (!uniqueId) {
      console.error('Product is missing an ID:', product); 
      return;
    }
  
    const cart = this.getCartItems();
    const existingProduct = cart.find((item) => item.id === uniqueId);
  
    if (existingProduct) {
      existingProduct.quantity += 1; 
    } else {
      cart.push({
        id: uniqueId,
        name: product.name,
        price: product.price,
        category: product.category,
        quantity: 1, 
      });
    }
  
    localStorage.setItem(this.CART_KEY, JSON.stringify(cart));
  }
  
  clearCart(): void {
    localStorage.removeItem(this.CART_KEY);
  }

  checkout(orderRequest: OrderRequest): Observable<unknown> {
    return this.http.post(`${this.apiUrl}/orders/create`, orderRequest);
  }
}