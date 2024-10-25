import { NgClass, NgFor, NgIf, NgStyle } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { Title } from '@angular/platform-browser';
import Cropper from 'cropperjs';
import 'cropperjs/dist/cropper.css';
import { PostService } from '../../services/post.service';
import { IProfileResponse } from '../../interfaces/i-profile-response';
import {getFirstCharacter} from '../../utils/formater';

@Component({
  selector: 'app-modal-create-post',
  standalone: true,
  imports: [
    MatIconModule,
    NgClass,
    NgIf,
    FormsModule,
    NgFor,
    NgStyle,
    ReactiveFormsModule,
  ],
  templateUrl: './modal-create-post.component.html',
  styleUrls: ['./modal-create-post.component.scss'],
})
export class ModalCreatePostComponent {
  constructor(private titleService: Title, private postService: PostService) {}

  getFirstCharacter = getFirstCharacter;
  
  @Input() user: IProfileResponse | undefined;
  @Output() closeModalCreate = new EventEmitter<void>();

  cropper: Cropper | null = null;
  isDragging = false;
  isFiles = false;
  fileUrl: string | ArrayBuffer | null = null;

  showRatioOptions = false;
  selectedAspectRatio: string = 'original';

  showZoomOptions = false;
  zoomValue: number = 1;

  showImagesOption = false;
  images: {
    url: string;
    selected: boolean;
    cropper?: Cropper;
    cropData?: Cropper.Data;
  }[] = [];

  draggedItemIndex: number | null = null;

  nextSubmit = false;

  desc: string = '';

  currentImageIndex = 0;

  postFormGroup: FormGroup = new FormGroup({
    desc: new FormControl(
      '',
      Validators.compose([Validators.required, Validators.maxLength(2200)])
    ),
  });

  ngOnInit() {
    this.titleService.setTitle('สร้างโพสต์ใหม่ • Instagram');
  }

  toggleRatioOptions() {
    this.showRatioOptions = !this.showRatioOptions;
  }

  toggleZoomOptions() {
    this.showZoomOptions = !this.showZoomOptions;
  }

  toggleImagesOptions() {
    this.showImagesOption = !this.showImagesOption;
  }

  setZoomValue(e: any) {
    const zoomValue = parseFloat(e.target.value);
    this.zoomValue = zoomValue;

    console.log(`Zoom value: ${zoomValue}`);

    if (this.cropper) {
      this.cropper.zoomTo(zoomValue);
    }
  }

  setAspectRatio(ratio: string) {
    this.selectedAspectRatio = ratio;
    this.showRatioOptions = false;

    switch (ratio) {
      case '1:1':
        this.applyAspectRatio(1, 1);
        break;
      case '4:5':
        this.applyAspectRatio(4, 5);
        break;
      case '16:9':
        this.applyAspectRatio(16, 9);
        break;
      default:
        this.applyOriginalAspectRatio();
        break;
    }
  }

  applyAspectRatio(widthRatio: number, heightRatio: number) {
    const previewImage = document.querySelector(
      '.preview-image'
    ) as HTMLImageElement | null;

    if (!previewImage) {
      console.error('Preview image not found.');
      return;
    }

    if (this.cropper) {
      this.cropper.destroy();
    }

    this.cropper = new Cropper(previewImage, {
      aspectRatio: widthRatio / heightRatio,
      crop: (event: any) => {
        console.log({
          x: event.detail.x,
          y: event.detail.y,
          width: event.detail.width,
          height: event.detail.height,
          rotate: event.detail.rotate,
          scaleX: event.detail.scaleX,
          scaleY: event.detail.scaleY,
        });
      },
    });
  }

  applyOriginalAspectRatio() {
    const previewImage = document.querySelector(
      '.preview-image'
    ) as HTMLImageElement | null;

    if (!previewImage) {
      console.error('Preview image not found.');
      return;
    }

    if (this.cropper) {
      this.cropper.destroy();
    }

    this.cropper = new Cropper(previewImage, {
      aspectRatio: NaN,
      crop: (event: any) => {
        console.log({
          x: event.detail.x,
          y: event.detail.y,
          width: event.detail.width,
          height: event.detail.height,
          rotate: event.detail.rotate,
          scaleX: event.detail.scaleX,
          scaleY: event.detail.scaleY,
        });
      },
    });
  }

  onCloseModalCreate() {
    this.titleService.setTitle('Instagram');

    this.images = [];
    this.currentImageIndex = 0;
    this.fileUrl = null;
    this.isFiles = false;
    this.nextSubmit = false;

    if (this.cropper) {
      this.cropper.destroy();
      this.cropper = null;
    }
    this.closeModalCreate.emit();
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
    this.isDragging = true;
  }

  onDragLeave(event: DragEvent) {
    event.preventDefault();
    this.isDragging = false;
  }

  onDropFile(event: DragEvent) {
    event.preventDefault();
    this.isDragging = false;
    const file = event.dataTransfer?.files[0];
    if (file) {
      this.previewFile(file);
    }
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.previewFile(input.files[0]);
    }
  }

  onBackToFileSelect() {
    this.images = [];
    this.currentImageIndex = 0;
    this.fileUrl = null;
    this.isFiles = false;
    this.nextSubmit = false;
    if (this.cropper) {
      this.cropper.destroy();
      this.cropper = null;
    }
  }

  onDragImageStart(event: DragEvent, index: number) {
    this.draggedItemIndex = index;

    const draggedElement = event.target as HTMLElement;
    (event.target as HTMLElement).style.opacity = '0.5';

    if (event.dataTransfer) {
      event.dataTransfer.setDragImage(draggedElement, 0, 0);
      event.dataTransfer.effectAllowed = 'move';
      event.dataTransfer.setData('text/plain', index.toString());
    }
  }

  onDragImageOver(event: DragEvent) {
    event.preventDefault();
    event.dataTransfer!.dropEffect = 'move';
  }

  onDropImage(event: DragEvent, index: number) {
    event.preventDefault();
    const draggedIndex = this.draggedItemIndex;

    if (draggedIndex !== null && draggedIndex !== index) {
      const movedImage = this.images[draggedIndex];
      this.images.splice(draggedIndex, 1);
      this.images.splice(index, 0, movedImage);
    }

    this.draggedItemIndex = null;
  }

  onDragImageEnd(event: DragEvent) {
    (event.target as HTMLElement).style.opacity = '1';
  }

  removeImage(index: number) {
    if (index < 0 || index >= this.images.length) {
      console.error('Invalid image index.');
      return;
    }

    this.images.splice(index, 1);

    if (this.images.length === 0) {
      this.fileUrl = null;
      this.isFiles = false;
      if (this.cropper) {
        this.cropper.destroy();
        this.cropper = null;
      }
      console.log('No images left.');
      return;
    }

    let newIndex = index >= this.images.length ? this.images.length - 1 : index;

    this.onSelectImage(newIndex);

    console.log(
      `Image at index ${index} removed. Selected image index is now ${newIndex}.`
    );
  }

  previewFile(file: File) {
    const reader = new FileReader();
    reader.onload = () => {
      this.fileUrl = reader.result;
      this.isFiles = true;

      this.images.push({ url: this.fileUrl as string, selected: true });

      this.images.forEach((img, index) => {
        if (index !== this.images.length - 1) {
          img.selected = false;
        }
      });

      this.initCropper();
    };

    reader.readAsDataURL(file);
  }

  onSelectImage(index: number) {
    if (index < 0 || index >= this.images.length) {
      console.error('Invalid image index after deletion.');
      return;
    }

    const currentImage = this.images.find((img) => img.selected);
    if (currentImage && this.cropper) {
      currentImage.cropData = this.cropper.getData();
      this.cropper.destroy();
    }

    this.images.forEach((img) => (img.selected = false));

    if (this.images[index]) {
      this.images[index].selected = true;

      const previewImage = document.querySelector(
        '.preview-image'
      ) as HTMLImageElement;

      if (previewImage) {
        previewImage.src = this.images[index].url;
        this.initCropper(index);
      } else {
        console.error('Preview image not found.');
      }
    } else {
      console.error('Image at the given index does not exist.');
    }
  }

  initCropper(index?: number) {
    setTimeout(() => {
      const previewImage = document.querySelector(
        '.preview-image'
      ) as HTMLImageElement;

      if (this.cropper) {
        this.cropper.destroy();
      }

      if (previewImage) {
        this.cropper = new Cropper(previewImage, {
          aspectRatio: NaN,
          crop: (event: any) => {
            console.log({
              x: event.detail.x,
              y: event.detail.y,
              width: event.detail.width,
              height: event.detail.height,
              rotate: event.detail.rotate,
              scaleX: event.detail.scaleX,
              scaleY: event.detail.scaleY,
            });
          },
          ready: () => {
            if (index !== undefined && this.images[index].cropData) {
              this.cropper!.setData(this.images[index].cropData!);
              console.log('Crop data applied:', this.images[index].cropData);
            }
          },
        });
      } else {
        console.error('Preview image not found.');
      }
    }, 0);
  }

  nextPost() {
    
    this.nextSubmit = true;
    
    this.images.forEach((image, i) => {
      this.onSelectImage(i);
    });


    if (this.images.length === 0) {
      console.warn('No images to process.');
      return;
    }

    const currentImage = this.images[this.currentImageIndex];

    if (this.cropper && currentImage) {
      const croppedCanvas = this.cropper.getCroppedCanvas();

      if (croppedCanvas) {
        const croppedImageUrl = croppedCanvas.toDataURL('image/jpeg');

        this.images[this.currentImageIndex] = {
          ...currentImage, 
          url: croppedImageUrl, 
          cropData: this.cropper.getData(), 
        };

        console.log('Cropped image:', this.images[this.currentImageIndex]);
      } else {
        console.warn('Cropped canvas is not valid.');
      }

      this.cropper.destroy();
      this.cropper = null; 

      if (this.currentImageIndex < this.images.length - 1) {
        this.currentImageIndex++;
      } else {
        this.currentImageIndex = 0; 
      }

      this.images.forEach((img, index) => {
        img.selected = index === this.currentImageIndex;
      });

      console.log('All images after processing:', this.images);
    } else {
      console.warn('No cropper available or current image is undefined.');
    }
  }

  onDescChange() {
    this.desc = this.postFormGroup.controls['desc'].value;
  }

  prevImage() {
    if (this.currentImageIndex > 0) {
      this.currentImageIndex--;
    } else {
      this.currentImageIndex = this.images.length - 1;
    }
  }

  nextImage() {
    if (this.currentImageIndex < this.images.length - 1) {
      this.currentImageIndex++;
    } else {
      this.currentImageIndex = 0;
    }
  }

  submitPost() {
    const desc = this.postFormGroup.controls['desc'].value;

    if (!desc || desc.trim().length === 0) {
      console.log('Description is required.');
      alert('Please provide a description.');
      return;
    }

    if (!this.images || this.images.length === 0) {
      console.log('No images uploaded.');
      alert('Please upload at least one image.');
      return;
    }

    const files: File[] = [];

    this.images.forEach((img) => {
      files.push(this.base64ImageToFile(img.url));
    });

    console.log(files, files);

    this.postService.create(desc, files).subscribe({
      next: (res) => {
        console.log(res);
        // update post
        this.onCloseModalCreate();
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }

  base64ImageToFile(str: string): File {
    const pos = str.indexOf(';base64,');
    const type = str.substring(5, pos);
    const b64 = str.substr(pos + 8);

    const imageContent = atob(b64);

    const buffer = new ArrayBuffer(imageContent.length);
    const view = new Uint8Array(buffer);

    for (let n = 0; n < imageContent.length; n++) {
      view[n] = imageContent.charCodeAt(n);
    }

    const blob = new Blob([view], { type: type });

    const now = new Date();
    const timestamp = now.toISOString().replace(/[:.-]/g, '');
    const fileExtension = type.split('/')[1];

    const fileName = `image_${timestamp}.${fileExtension}`;

    return new File([blob], fileName, { type: type });
  }
}
