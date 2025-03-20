import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router} from '@angular/router';
import { ProductsService } from '../../services/products.service';
import { CartService } from '../../services/cart.service';
import { Product } from '../../modules/shared/types/products.types';

@Component({
  selector: 'app-products-details',
  standalone: false,
  templateUrl: './products-details.component.html',
  styleUrl: './products-details.component.scss'
})
export class ProductsDetailsComponent implements OnInit {
  protected product: Product = {
    id: 0,
    name: '',
    category: '',
    price: 0
  }; 
  protected productId: number = 0; 

  constructor(
    private productsService: ProductsService,
    private cartService: CartService,
    private activatedRoute: ActivatedRoute,
    private router: Router    
  ) {}

  ngOnInit(): void {
    this.productId = Number(this.activatedRoute.snapshot.paramMap.get('id'));

    this.productsService.getProductById(this.productId).subscribe({
      next: (data) => {
        this.product = data;
      },
      error: (err) => {
        console.error('Error fetching product details:', err);
      }
    });
  }

  addToCart(): void {
    this.cartService.addToCart(this.product);
    alert(`${this.product.name} has been added to the cart!`);
  }
  
  deleteProduct(): void {
    if (confirm('Are you sure you want to delete this product?')) {
      this.productsService.deleteProduct(this.productId).subscribe({
        next: () => {
          alert('Product deleted successfully!');
          this.router.navigate(['/products']);
        },
        error: (err) => {
          console.error('Error deleting product:', err);
          alert('There was an error deleting the product. Please try again.');
        }
      });
    }
  }
}