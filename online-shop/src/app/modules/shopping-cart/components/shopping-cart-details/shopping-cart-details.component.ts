import { Component, OnInit } from '@angular/core';
import { CartService } from '../../../../services/cart.service';
import { OrderRequest } from '../../../shared/types/order-request.types'; 
import { CartProduct } from '../../../shared/types/cart-product.types';

@Component({
  selector: 'app-shopping-cart-details',
  standalone: false,
  templateUrl: './shopping-cart-details.component.html',
  styleUrl: './shopping-cart-details.component.scss'
})
export class ShoppingCartDetailsComponent implements OnInit {
  products: CartProduct[] = [];

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.products = this.cartService.getCartItems();
  }

  get totalPrice(): number {
    return this.products.reduce((sum, product) => sum + product.price * product.quantity, 0);
  }

  removeProduct(productId: number): void {
    this.products = this.products.filter(product => product.id !== productId);
    localStorage.setItem('shoppingCart', JSON.stringify(this.products));
  }

  checkout(): void {
    const orderRequest : OrderRequest = {
      orderTimestamp: new Date(),
      addressId: 1, 
      locationId: 1, 
      products: this.products.map(product => ({
        productId: product.id,
        quantity: product.quantity
      }))
    };

    this.cartService.checkout(orderRequest).subscribe({
      next: () => {
        alert('Order placed successfully!');
        this.cartService.clearCart();
        this.products = [];
      },
      error: (err) => {
        console.error('Error creating order:', err);
      }
    });
  }
}