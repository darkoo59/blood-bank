import { Component, OnInit } from '@angular/core';
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { catchError, EMPTY, take } from 'rxjs';
import { News } from 'src/app/model/news';
import { NewsService } from './news.service';

@Component({
  selector: 'app-sending-news',
  templateUrl: './sending-news.component.html',
  styleUrls: ['./sending-news.component.css']
})
export class SendingNewsComponent implements OnInit {

  constructor(private newsService: NewsService, private m_Router: Router,  private m_SnackBar: MatSnackBar) { }

  ngOnInit() {
  }

  form: UntypedFormGroup = new UntypedFormGroup({
    'title': new FormControl(null, Validators.required),
    'content': new FormControl(null, Validators.required)
  })
  m_Errors: string[] = [];

  onSubmit() : void {

    this.m_Errors.length = 0;

    this.form.updateValueAndValidity();
    if (!this.form.valid) return;
    const dto: News = {
      title: this.form.get('title')?.value,
      content: this.form.get('content')?.value
    }

    this.newsService.sendNews(dto)
      .pipe(catchError(res => {
        console.log(res);
        this.m_Router.navigate(['/home'])
        const error = res.error;
        if (error && error.message) {
          this.m_Errors.push(error.message);
          return EMPTY;
        }
        this.m_Errors.push(error);
        return EMPTY;
      }))
      .subscribe(data => {
        this.m_SnackBar.open(`Successfully sended news to hospital`, 'Close', { duration: 5000 })
        this.m_Router.navigate(['/home'])
      });
  }
}
