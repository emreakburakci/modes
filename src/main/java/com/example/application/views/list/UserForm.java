package com.example.application.views.list;

import com.example.application.data.entities.User;
import com.example.application.utils.MD5Utils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;

import java.io.IOException;


public class UserForm extends FormLayout {
    TextField identityNumber = new TextField("Identity Number");
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField password = new TextField("Password");
    EmailField email = new EmailField("Email");
    TextField country = new TextField("Country");
    TextField region = new TextField("Region");
    TextField subregion = new TextField("Subregion");
    TextField district = new TextField("District");
    TextField postalCode = new TextField("Postal Code");
    TextField phoneNumber = new TextField("Phone Number");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    MemoryBuffer bufferFront = new MemoryBuffer();
    MemoryBuffer bufferLeft = new MemoryBuffer();

    MemoryBuffer bufferRight = new MemoryBuffer();

    Upload uploadFront = new Upload(bufferFront);

    Upload uploadLeft = new Upload(bufferLeft);

    Upload uploadRight = new Upload(bufferRight);


    BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

    public UserForm() {
        addClassName("user-form");
        binder.bindInstanceFields(this);


        add(identityNumber, firstName,
                lastName, password,
                email, country, region, subregion, district, postalCode, uploadFront, uploadRight, uploadLeft, phoneNumber,

                createButtonsLayout());


        uploadFront.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        uploadFront.setMaxFiles(1);
        uploadFront.setDropLabel(new Div("Front Photo"));
        uploadFront.addSucceededListener(event -> {
            byte[] fileContent = new byte[0];
            try {
                fileContent = bufferFront.getInputStream().readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            binder.getBean().setPictureFront(fileContent);
        });

        uploadRight.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        uploadRight.setMaxFiles(1);
        uploadRight.setDropLabel(new Div("Right Photo"));

        uploadRight.addSucceededListener(event -> {
            byte[] fileContent = new byte[0];
            try {
                fileContent = bufferRight.getInputStream().readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            binder.getBean().setPictureRight(fileContent);
        });

        uploadLeft.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        uploadLeft.setMaxFiles(1);
        uploadLeft.setDropLabel(new Div("Left Photo"));

        uploadLeft.addSucceededListener(event -> {
            byte[] fileContent = new byte[0];
            try {
                fileContent = bufferLeft.getInputStream().readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            binder.getBean().setPictureLeft(fileContent);
        });

    }

    public void setUser(User user) {

        binder.setBean(user);

        
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
            User user = binder.getBean();
            user.setPassword(MD5Utils.generateMD5(password.getValue()));
            //user.setPassword(MD5Utils.encrypt(password.getValue()));

            fireEvent(new SaveEvent(this, user));
        }
    }


    public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
        private User user;

        protected UserFormEvent(UserForm source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class SaveEvent extends UserFormEvent {
        SaveEvent(UserForm source, User user) {
            super(source, user);
        }
    }

    public static class DeleteEvent extends UserFormEvent {
        DeleteEvent(UserForm source, User user) {
            super(source, user);
        }

    }

    public static class CloseEvent extends UserFormEvent {
        CloseEvent(UserForm source) {
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