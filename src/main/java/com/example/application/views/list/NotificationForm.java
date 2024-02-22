package com.example.application.views.list;

import com.example.application.data.entities.Notification;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;


public class NotificationForm extends FormLayout {
    TextField title = new TextField("title");
    TextField content = new TextField("content");
    TextField status = new TextField("status");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    BeanValidationBinder<Notification> binder = new BeanValidationBinder<>(Notification.class);

    public NotificationForm() {
        addClassName("notification-form");
        binder.bindInstanceFields(this);

        add(title, content, status, createButtonsLayout());

    }

    public void setNotification(Notification notification) {

        binder.setBean(notification);


    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            Notification notification = binder.getBean();

            fireEvent(new SaveEvent(this, notification));
        }
    }


    public static abstract class NotificationFormEvent extends ComponentEvent<NotificationForm> {
        private Notification notification;

        protected NotificationFormEvent(NotificationForm source, Notification notification) {
            super(source, false);
            this.notification = notification;
        }

        public Notification getNotification() {
            return notification;
        }
    }

    public static class SaveEvent extends NotificationFormEvent {
        SaveEvent(NotificationForm source, Notification notification) {
            super(source, notification);
        }
    }

    public static class DeleteEvent extends NotificationFormEvent {
        DeleteEvent(NotificationForm source, Notification notification) {
            super(source, notification);
        }

    }

    public static class CloseEvent extends NotificationFormEvent {
        CloseEvent(NotificationForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}