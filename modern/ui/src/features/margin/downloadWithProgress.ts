/**
 * Download URL with real XHR progress (no fake timers).
 * Uses onprogress when server sends Content-Length; otherwise step-based.
 */
export interface DownloadWithProgressOptions {
  url: string;
  onProgress?: (loaded: number, total: number | null, pct: number | null) => void;
  onComplete: (blob: Blob) => void;
  onError: (err: string) => void;
}

export function downloadWithProgress(options: DownloadWithProgressOptions): void {
  const { url, onProgress, onComplete, onError } = options;
  const xhr = new XMLHttpRequest();
  xhr.open('GET', url, true);
  xhr.responseType = 'arraybuffer';

  xhr.onprogress = () => {
    const total = xhr.getResponseHeader('Content-Length');
    const totalNum = total ? parseInt(total, 10) : null;
    const loaded = xhr.response?.byteLength ?? 0;
    const pct = totalNum != null && totalNum > 0 ? Math.min(100, Math.round((loaded / totalNum) * 100)) : null;
    onProgress?.(loaded, totalNum, pct);
  };

  xhr.onload = () => {
    if (xhr.status >= 200 && xhr.status < 300) {
      const blob = new Blob([xhr.response], {
        type: xhr.getResponseHeader('Content-Type') ?? 'application/octet-stream',
      });
      onComplete(blob);
    } else {
      onError(`HTTP ${xhr.status}`);
    }
  };

  xhr.onerror = () => onError('Ошибка сети');
  xhr.ontimeout = () => onError('Таймаут');
  xhr.send();
}

export function saveBlobAsFile(blob: Blob, filename: string): void {
  const a = document.createElement('a');
  a.href = URL.createObjectURL(blob);
  a.download = filename;
  a.click();
  URL.revokeObjectURL(a.href);
}
