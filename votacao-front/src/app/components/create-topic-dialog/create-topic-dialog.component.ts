import { Component } from '@angular/core';
DialogRef

@Component({
  selector: 'app-create-topic-dialog',
  standalone: true,
  imports: [

  ],
  templateUrl: './create-topic-dialog.component.html',
  styleUrl: './create-topic-dialog.component.scss'
})
export class CreateTopicDialogComponent {

  readonly dialogRef = inject(MatDialogRef<DialogOverviewExampleDialog>);
  readonly data = inject<DialogData>(MAT_DIALOG_DATA);
  readonly animal = model(this.data.animal);

  onNoClick(): void {
    this.dialogRef.close();
  }

}
