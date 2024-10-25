import { Component } from '@angular/core';
import { LSidebarComponent } from "../l-sidebar/l-sidebar.component";
import { MainLayoutComponent } from "../../layouts/main-layout/main-layout.component";
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-direct-messages',
  standalone: true,
  imports: [LSidebarComponent, MainLayoutComponent],
  templateUrl: './direct-messages.component.html',
  styleUrl: './direct-messages.component.scss'
})
export class DirectMessagesComponent {
  constructor(private titleService: Title){}

  ngOnInit(){
    this.titleService.setTitle('กล่องข้อความ • Direct')
  }
}
