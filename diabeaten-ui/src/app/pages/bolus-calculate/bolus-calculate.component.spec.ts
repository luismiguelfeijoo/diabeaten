import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BolusCalculateComponent } from './bolus-calculate.component';

describe('BolusCalculateComponent', () => {
  let component: BolusCalculateComponent;
  let fixture: ComponentFixture<BolusCalculateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BolusCalculateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BolusCalculateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
