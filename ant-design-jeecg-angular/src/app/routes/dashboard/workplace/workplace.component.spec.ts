import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardWorkplaceComponent } from './workplace.component';

describe('DashboardWorkplaceComponent', () => {
  let component: DashboardWorkplaceComponent;
  let fixture: ComponentFixture<DashboardWorkplaceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardWorkplaceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardWorkplaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
