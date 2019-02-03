package com.vaadin.starter.skeleton;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class CustomerForm extends FormLayout {

    private static final long serialVersionUID = 7472189324279307792L;

    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");

    private ComboBox<CustomerStatus> status = new ComboBox<>("Status");

    private DatePicker birthDate = new DatePicker("BirthDay");

    private CustomerService service = CustomerService.getInstance();
    private Customer customer;
    private MainView mainview;

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Customer> binder = new Binder<>(Customer.class);

    public CustomerForm(MainView mainView){
        this.mainview = mainView;
        status.setItems(CustomerStatus.values());
        binder.bindInstanceFields(this);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickListener(e-> this.save());
        delete.addClickListener(e-> this.delete());
        add(firstName, lastName, status, birthDate, buttons);

        setCustomer(null);
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
        binder.setBean(customer);

        boolean enabled = customer!=null;
        setVisible(enabled);

        if(enabled) { 
            firstName.focus();
        }
    }

    private void delete(){
        service.delete(customer);
        mainview.updateList();
        setCustomer(null);
    }

    private void save(){
        service.save(customer);
        mainview.updateList();
        setCustomer(null);
    }

}