import { NgFor } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-story',
  standalone: true,
  imports: [NgFor, MatIconModule],
  templateUrl: './story.component.html',
  styleUrl: './story.component.scss',
})
export class StoryComponent {
  stories = [
    { username: 'User1', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User2', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User3', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User4', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User5', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User1', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User2', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User3', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User4', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User5', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User1', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User2', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User3', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User4', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
    { username: 'User5', image: 'https://i.imgur.com/ZzQ1Q0G.png' },
  ];

  @ViewChild('storyContainer') storyContainer!: ElementRef;


  

  scrollLeft() {
    console.log(this.storyContainer);
    this.storyContainer.nativeElement.scrollBy({
      left: -200, 
      behavior: 'smooth',
    });
  }

  scrollRight() {
    console.log(this.storyContainer);
    this.storyContainer.nativeElement.scrollBy({
      left: 200,
      behavior: 'smooth',
    });
  }
}
