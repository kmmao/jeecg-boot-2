import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { OutdoorReportComponent } from './report.component';

describe('OutdoorReportComponent', () => {
  let component: OutdoorReportComponent;
  let fixture: ComponentFixture<OutdoorReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OutdoorReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OutdoorReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
