export interface IProfileResponse {
  id: string;
  email: string;
  username: string;
  bio: string;
  profilePictureUrl: string;
  followedByCurrentUser: boolean;
  followerCount: number;
  followingCount: number;
}
