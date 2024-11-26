import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListaSimple {

    public Nodo inicio;
    public int tamano;

    public ListaSimple() {
        inicio = null;
        tamano = 0;
    }

    boolean estavacia(){
        return inicio == null;
    }
    public void agregarHeroe(SpiderverseHero hero, JTable jTable) {
        if (estavacia()) {
            inicio = new Nodo(hero);
        } else {
            if (!existeHeroe(hero.getCodigo())) {
                Nodo nuevoNodo = new Nodo(hero);
                nuevoNodo.siguiente = inicio;
                inicio = nuevoNodo;
            } else {
                JOptionPane.showMessageDialog(null, "El código ya existe. No se puede registrar.");
                return;
            }
        }
        tamano++;
        actualizarTabla(jTable);
    }
    public boolean existeHeroe(int codigo) {
        Nodo actual = inicio;
        while (actual != null) {
            if (actual.hero.getCodigo() == codigo) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public void actualizarTabla(JTable jTable) {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        model.setRowCount(0);
        Nodo actual = inicio;
        while (actual != null) {
            model.addRow(new Object[]{
                    actual.hero.getCodigo(),
                    actual.hero.getNombre(),
                    actual.hero.getPoderesEspeciales(),
                    actual.hero.getUniverso(),
                    actual.hero.getNivelExperiencia()
            });
            actual = actual.siguiente;
        }
    }

    public SpiderverseHero buscarPorCodigo(int codigo) {
        Nodo actual = inicio;
        while (actual != null) {
            if (actual.hero.getCodigo() == codigo) {
                return actual.hero;
            }
            actual = actual.siguiente;
        }
        JOptionPane.showMessageDialog(null, "Héroe no encontrado.");
        return null;
    }

    public ListaSimple filtrarHeroes(String poderEspecial, JTable table) {
        ListaSimple listaFiltrada = new ListaSimple();
        Nodo actual = inicio;

        while (actual != null) {
            if (!actual.hero.getPoderesEspeciales().equals(poderEspecial)) {
                listaFiltrada.agregarHeroe(actual.hero, table);
            }
            actual = actual.siguiente;
        }
        return listaFiltrada;

    }

    public void ordenarPorNivelExperiencia() {
        if (inicio == null || inicio.siguiente == null) {
            return;
        }

        boolean sorted;
        do {
            sorted = true;
            Nodo actual = inicio;
            while (actual.siguiente != null) {
                if (actual.hero.getNivelExperiencia() > actual.siguiente.hero.getNivelExperiencia()) {
                    SpiderverseHero temp = actual.hero;
                    actual.hero = actual.siguiente.hero;
                    actual.siguiente.hero = temp;
                    sorted = false;
                }
                actual = actual.siguiente;
            }
        } while (!sorted);
    }

    public void listarHeroes(JTable table) {
        actualizarTabla(table);
    }


    private int contarHeroesRecursivo(String universo, Nodo actual) {
        if (actual == null) {
            return 0;
        }
        int c = 0;

        if (actual.hero.getUniverso().equals(universo)) {
            c = 1;
        }
        return c + contarHeroesRecursivo(universo, actual.siguiente);
    }
    public int contarHeroesUniverso(String universo) {
        return contarHeroesRecursivo(universo, inicio);
    }

}
