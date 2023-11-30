package viewmodel;

    public class Seller {
        private int id;
        private String nombre;

        public Seller(int id, String nombre, String oficio) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }
    }


