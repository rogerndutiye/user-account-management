import React from 'react';
import { toast, ToastContainer } from 'react-toastify';
export const successNotification=(message) =>
toast.success(message, {
    position: toast.POSITION.TOP_RIGHT
});

export const infoNotification=(message) =>
toast.info(message, {
    position: toast.POSITION.TOP_RIGHT
});

export const warningNotification=(message) =>
toast.warning(message, {
    position: toast.POSITION.TOP_RIGHT
});

export const errorNotification=(message) =>
toast.error(message, {
    position: toast.POSITION.TOP_RIGHT
});

export const notify = () => {
    toast("Default Notification !");

    toast.success("Success Notification !", {
      position: toast.POSITION.TOP_CENTER
    });

    toast.error("Error Notification !", {
      position: toast.POSITION.TOP_LEFT
    });

    toast.warn("Warning Notification !", {
      position: toast.POSITION.BOTTOM_LEFT
    });

    toast.info("Info Notification !", {
      position: toast.POSITION.BOTTOM_CENTER
    });

    toast("Custom Style Notification with css class!", {
      position: toast.POSITION.BOTTOM_RIGHT,
      className: 'foo-bar'
    });
  };

