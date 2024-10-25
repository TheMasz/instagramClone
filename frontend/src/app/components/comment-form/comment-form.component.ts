import { Component, Input } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommentService } from '../../services/comment.service';
import { IGetPostResponse } from '../../interfaces/i-get-post-response';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-comment-form',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './comment-form.component.html',
  styleUrl: './comment-form.component.scss',
})
export class CommentFormComponent {
  constructor(private commentService: CommentService) {}

  @Input() post!: IGetPostResponse;

  message: string = '';

  commentFormGroup: FormGroup = new FormGroup({
    message: new FormControl('', Validators.required),
  });

  onMessageChange() {
    this.message = this.commentFormGroup.controls['message'].value;
  }

  submitComment() {
    if (this.commentFormGroup.invalid) {
      return;
    }

    const message = this.commentFormGroup.controls['message'].value;

    this.commentService.create(this.post.id, message).subscribe({
      next: (res) => {
        this.post.comments.push(res);
        this.message = '';
        this.commentFormGroup.reset();
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }
}
