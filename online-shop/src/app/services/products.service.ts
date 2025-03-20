import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { map } from 'rxjs/operators';
import { Product } from '../modules/shared/types/products.types';
import { RawProduct } from '../modules/shared/types/raw-product.types';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getProducts(): Observable<Product[]> {
    return this.http.get<RawProduct[]>(`${this.apiUrl}/products/getAll`).pipe(
      map(products => products.map(product => ({
        id: product.productId,
        name: product.productName,
        category: product.categoryName ? product.categoryName : 'Uncategorized',
        price: product.price,
      })))
    );
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<RawProduct>(`${this.apiUrl}/products/getById/${id}`).pipe(
      map(product => ({
        id: product.productId,
        name: product.productName,
        category: product.categoryName ? product.categoryName : 'Uncategorized',
        price: product.price,
        description: product.productDescription,
      }))
    );
  }

  deleteProduct(productId: number): Observable<unknown> {
    return this.http.delete<void>(`${this.apiUrl}/products/delete/${productId}`);
  }
}
