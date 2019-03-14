import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DemoOneToManyComponent } from './one-to-many.component';

describe('DemoOneToManyComponent', () => {
  let component: DemoOneToManyComponent;
  let fixture: ComponentFixture<DemoOneToManyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemoOneToManyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemoOneToManyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
