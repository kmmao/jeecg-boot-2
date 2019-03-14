import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DemoPrintDemoListComponent } from './print-demo-list.component';

describe('DemoPrintDemoListComponent', () => {
  let component: DemoPrintDemoListComponent;
  let fixture: ComponentFixture<DemoPrintDemoListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemoPrintDemoListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemoPrintDemoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
