import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusDialogComponent } from './status-dialog.component';
import {  MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MaterialModule } from 'src/app/material/material.module';

describe('StatusDialogComponent', () => {
  let component: StatusDialogComponent;
  let fixture: ComponentFixture<StatusDialogComponent>;
  const mockDialogRef = {
    close: jasmine.createSpy('close')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatusDialogComponent ],
      imports: [MatDialogModule, MaterialModule],
      providers: [
        {
          provide: MatDialogRef,
          useValue: mockDialogRef
        },
        {
          provide: MAT_DIALOG_DATA,
          useValue: {}
        }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatusDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
