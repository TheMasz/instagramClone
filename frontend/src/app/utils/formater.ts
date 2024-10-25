export function formatDate(dateString: string): string {
  const now = new Date();
  const date = new Date(dateString);

  const diffMs = now.getTime() - date.getTime();
  const diffMinutes = Math.floor(diffMs / (1000 * 60));
  const diffHours = Math.floor(diffMs / (1000 * 60 * 60));
  const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24));

  if (diffDays > 0) {
    return diffDays === 1 ? '1 วัน' : `${diffDays} วัน`;
  } else if (diffHours > 0) {
    return diffHours === 1 ? '1 ชั่วโมง' : `${diffHours} ชั่วโมง`;
  } else if (diffMinutes > 0) {
    return diffMinutes === 1 ? '1 นาที' : `${diffMinutes} นาที`;
  } else {
    return 'เมื่อสักครู่';
  }
}

export function getFirstCharacter(input: string): string {
  if (!input) {
    return ''; 
  }
  return input.charAt(0); 
}