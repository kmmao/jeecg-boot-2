import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DemoHelloworldComponent } from './helloworld.component';

describe('DemoHelloworldComponent', () => {
  let component: DemoHelloworldComponent;
  let fixture: ComponentFixture<DemoHelloworldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemoHelloworldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemoHelloworldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
