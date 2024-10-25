export interface IGetPostResponse {
  id: string;
  description: string;
  images: string[];
  createdAt: string;

  userId: string;
  username: string;
  userProfile: string;

  likeCount: number;
  isLikedByCurrentUser: boolean;

  isSavedByCurrentUser: boolean;

  comments: any[];

}
