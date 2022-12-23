import { Component, OnInit } from '@angular/core';
import { Complaint } from 'src/app/model/complaint.model';
import { ComplaintResponseDTO, ComplaintsService } from './complaints.service';
import { MatDialog } from '@angular/material/dialog'
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { catchError, EMPTY } from 'rxjs';

@Component({
  selector: 'app-complaints',
  templateUrl: './complaints.component.html',
  styleUrls: ['./complaints.component.css']
})
export class ComplaintsComponent implements OnInit {

  constructor(private complaintsService: ComplaintsService) { }
  selectedComplaint?: Complaint;

  columns = [
    {
      columnDef: 'id',
      header: 'ID',
      cell: (complaint: Complaint) => `${complaint.id}`,
    },
    { 
      columnDef: 'text',
      header: 'Complaint',
      cell: (complaint: Complaint) => `${complaint.text}`,
    },
    {
      columnDef: 'user',
      header: 'User',
      cell: (complaint:Complaint) => `${complaint.user.firstname} ${complaint.user.lastname}`,
    },
    {
      columnDef: 'branchCenter',
      header: 'Branch Center',
      cell: (complaint:Complaint) => `${complaint.branchCenter.name}`,
    }
  ];

  ngOnInit() {
  }

  dataSource = this.complaintsService.getComplaints();
  displayedColumns = this.columns.map(c => c.columnDef)

  respond(complaint: Complaint) : void{
    console.log(complaint)
    this.selectedComplaint = complaint; 
  }

  form: UntypedFormGroup = new UntypedFormGroup({
    'response-text': new FormControl(null, Validators.required)
  });

  m_Errors: string[] = [];

  onSubmit(): void {
    this.m_Errors.length = 0;
    this.form.updateValueAndValidity();
    if(this.selectedComplaint == null) return;
    if(!this.form.valid) return;
    
    const dto: ComplaintResponseDTO = {
      id: this.selectedComplaint.id,
      text: this.form.get('response-text')?.value
    }
    console.log(dto)

    this.complaintsService.respond(dto)
      .pipe(catchError(res => {
        console.log(res);
        const error = res.error;
        if (error && error.message) {
          this.m_Errors.push(error.message);
          return EMPTY;
        }
        this.m_Errors.push(error);
        return EMPTY;
      }))
      .subscribe(_ => {
        this.dataSource = this.complaintsService.getComplaints()
        this.form.reset();
      })

  }


}
