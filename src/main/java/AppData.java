import Modelos.Obra;
import Modelos.Socio;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class AppData implements Serializable{
    private static final long serialVersionUID = 1L;
    private static AppData instance;
    private static final String FILE_NAME = "appdata.properties";

    private int duracaoEmprestimo;
    private int limiteEmprestimosSim;
    private float multaDiaria;
    private float anualidade;
    private LinkedList<Obra> obras; //???
    private HashMap<Integer, Socio> socios; //???


    private AppData() {
        // Private constructor to prevent instantiation
        loadFromFile();
    }

    public static synchronized AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                AppData loadedData = (AppData) ois.readObject();
                // Copy loaded data to this instance
                this.duracaoEmprestimo = loadedData.duracaoEmprestimo;
                this.limiteEmprestimosSim = loadedData.limiteEmprestimosSim;
                this.multaDiaria = loadedData.multaDiaria;
                this.anualidade = loadedData.anualidade;
                this.obras = loadedData.obras;
                this.socios = loadedData.socios;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                // Initialize with default values if loading fails
                initializeDefaultValues();
            }
        } else {
            initializeDefaultValues();
        }
    }

    private void initializeDefaultValues() {
        duracaoEmprestimo = 0;
        limiteEmprestimosSim = 0;
        multaDiaria = 0;
        anualidade = 0;
        obras = new LinkedList<>();
        socios = new HashMap<>();
    }




    public int getDuracaoEmprestimo() {
        return duracaoEmprestimo;
    }

    public void setDuracaoEmprestimo(int duracaoEmprestimo) {
        this.duracaoEmprestimo = duracaoEmprestimo;
    }

    public int getLimiteEmprestimosSim() {
        return limiteEmprestimosSim;
    }

    public void setLimiteEmprestimosSim(int limiteEmprestimosSim) {
        this.limiteEmprestimosSim = limiteEmprestimosSim;
    }

    public float getMultaDiaria() {
        return multaDiaria;
    }

    public void setMultaDiaria(float multaDiaria) {
        this.multaDiaria = multaDiaria;
    }

    public float getAnualidade() {
        return anualidade;
    }

    public void setAnualidade(float anualidade) {
        this.anualidade = anualidade;
    }

    public LinkedList<Obra> getObras() {
        return new LinkedList<>(obras);
    }

    public void pagarAnuidade(Socio socio) {
        if (socio == null) {
            return;
        }
        socio.pagarAnuidade();
    }

    public void pagarMulta(Socio socio) {
        if (socio == null) {
            return;
        }
        socio.pagarDivida(socio.getValorEmDivida());
    }
}