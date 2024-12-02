module se.verran.javafxhibernatedemo {
    requires javafx.controls;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens se.verran.javafxhibernatedemo.entities to org.hibernate.orm.core;
    exports se.verran.javafxhibernatedemo;
}