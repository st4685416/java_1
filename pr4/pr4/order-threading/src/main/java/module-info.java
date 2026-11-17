module order.threading {
    requires order.processing;
    requires order.storage;
    requires net.datafaker;

    exports com.order.threading;
}
