/**
 * Global UX feedback: AntD Message (loading) + Notification (success/error).
 * TASK-0034: success/error via notification — survives redirect, visible 4–5s.
 * Uses antd App context when available (no "Static function can not consume context" warning).
 */
import { message as staticMessage, notification as staticNotification } from 'antd';
import type { MessageInstance } from 'antd/es/message/interface';
import type { NotificationInstance } from 'antd/es/notification/interface';

let appMessage: MessageInstance | null = null;
let appNotification: NotificationInstance | null = null;

/** Set by FeedbackBridge (inside antd App). Use App context for message/notification. */
export function setFeedbackApp(inst: { message: MessageInstance; notification: NotificationInstance }): void {
  appMessage = inst.message;
  appNotification = inst.notification;
}

const LOADING_KEY = 'dcl-mutation-loading';
const NOTIFICATION_DURATION = 4;
const NOTIFICATION_PLACEMENT = 'topRight' as const;

const message = () => appMessage ?? staticMessage;
const notification = () => appNotification ?? staticNotification;

export function showSuccess(msg: string): void {
  message().success(msg);
}

export function showError(msg: string): void {
  message().error(msg);
}

/** Show success notification. Survives navigation. Use for save/create/update/delete. */
export function notifySuccess(title: string, description?: string): void {
  notification().success({
    message: title,
    description,
    duration: NOTIFICATION_DURATION,
    placement: NOTIFICATION_PLACEMENT,
  });
}

/** Show error notification. Survives navigation. Use for save/create/update/delete errors. */
export function notifyError(title: string, description?: string): void {
  notification().error({
    message: title,
    description,
    duration: NOTIFICATION_DURATION,
    placement: NOTIFICATION_PLACEMENT,
  });
}

export function showLoading(msg: string = 'Сохранение...'): void {
  message().loading({ content: msg, key: LOADING_KEY });
}

export function hideLoading(): void {
  (appMessage ?? staticMessage).destroy(LOADING_KEY);
}

/** Pending flash to show after redirect. Consumed by App on location change. */
let pendingFlash: { title: string; description?: string } | null = null;

/** Set success flash before navigate. App will consume and show notification once. */
export function setFlashSuccess(title: string, description?: string): void {
  pendingFlash = { title, description };
}

/** Consume pending flash and show notification. Called by App on location change. */
export function consumeFlash(): void {
  if (pendingFlash) {
    notifySuccess(pendingFlash.title, pendingFlash.description);
    pendingFlash = null;
  }
}

/** @deprecated Use setFlashSuccess. Kept for compatibility. */
export function setFlashError(msg: string): void {
  notifyError(msg);
}
