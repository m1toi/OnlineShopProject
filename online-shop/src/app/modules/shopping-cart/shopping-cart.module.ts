import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShoppingCartDetailsComponent } from './components/shopping-cart-details/shopping-cart-details.component';
import { IconButtonComponent } from '../shared/components/icon-button/icon-button.component';


@NgModule({
  declarations: [ShoppingCartDetailsComponent],
  imports: [
    CommonModule,
    IconButtonComponent
  ],
  exports: [ShoppingCartDetailsComponent], 

})
export class ShoppingCartModule { }
