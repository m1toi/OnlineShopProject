import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-icon-button',
  templateUrl: './icon-button.component.html',
  styleUrl: './icon-button.component.scss',
  standalone: true
})
export class IconButtonComponent {
  @Input() icon!: string;

  @Output() buttonClick = new EventEmitter<void>();
}
