import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LSidebarComponent } from './l-sidebar.component';

describe('LSidebarComponent', () => {
  let component: LSidebarComponent;
  let fixture: ComponentFixture<LSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LSidebarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
