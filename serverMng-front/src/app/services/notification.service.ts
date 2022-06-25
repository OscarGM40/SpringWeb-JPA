import { Injectable } from "@angular/core";
import { NotifierService } from "angular-notifier";

@Injectable({ providedIn: "root", })
export class NotificationService {
  private readonly notifier: NotifierService;

  constructor(notifierService: NotifierService) {
    this.notifier = notifierService;
  }

  onDefault(message: string): void {
    this.notifier.notify(TypeOfNotification.DEFAULT, message);
  }

  onSuccess(message: string): void {
    this.notifier.notify(TypeOfNotification.SUCCESS, message);
  }

  onError(message: string): void {
    this.notifier.notify(TypeOfNotification.ERROR, message);
  }

  onWarning(message: string): void {
    this.notifier.notify(TypeOfNotification.WARNING, message);
  }

  onInfo(message: string): void {
    this.notifier.notify(TypeOfNotification.INFO, message);
  }
}

enum TypeOfNotification {
  DEFAULT = "default",
  SUCCESS = "success",
  ERROR = "error",
  WARNING = "warning",
  INFO = "info",
}