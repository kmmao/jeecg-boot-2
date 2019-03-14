import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardMonitorComponent } from './monitor.component';

describe('DashboardMonitorComponent', () => {
  let component: DashboardMonitorComponent;
  let fixture: ComponentFixture<DashboardMonitorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardMonitorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardMonitorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
