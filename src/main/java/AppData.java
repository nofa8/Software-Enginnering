import Modelos.*;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private LinkedList<Emprestimo> emprestimos;
    private LinkedList<Reserva> reservas;

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
                this.emprestimos = loadedData.emprestimos;
                this.reservas = loadedData.reservas;
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
        emprestimos = new LinkedList<>();
        reservas = new LinkedList<>();
    }


    public LinkedList<Emprestimo> getEmprestimos() {
        if(this.emprestimos == null){
            this.emprestimos = new LinkedList<>();
        }
        return new LinkedList<>(emprestimos);
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

    public Socio pagarAnuidade(Socio socio) {
        if (socio == null) {
            return null;
        }
        socio.pagarAnuidade();
        return socio;
    }

    public Socio pagarMulta(Socio socio) {
        if (socio == null) {
            return null;
        }
        socio.pagarDivida(socio.getValorEmDivida());
        return socio;
    }

    public int adicionarObra(Obra obra) {
        if (obra == null) {
            return -1;
        }
        if (obras.contains(obra)){
            return 2;
        }
        for (Obra obraexistente : obras) {
            if (obraexistente.getISBN().equals(obra.getISBN())) {
                return 1;
            }
        }
        obras.add(obra);
        return 0;
    }

    public int adicionarSocio(Socio socio) {
        if (socio == null) {
            return -1;
        }
        if (socios.containsKey(socio.getNumero())) {
            return -2;
        }
        socios.put(socio.getNumero(), socio);
        return 0;
    }

    public boolean removerSocio(int numero) {
        if (!socios.containsKey(numero)) {
            return false;
        }
        Socio socio = socios.get(numero);
        if (socio.getEmprestimosAtuais().size() > 0) {
            return false;
        }

        return socios.remove(numero) != null;
    }

    public LinkedList<Reserva> getReservas() {
        if(this.reservas == null){
            this.reservas = new LinkedList<Reserva>();
        }

        return reservas;

    }

    public int editarObra(Obra nova, Obra antiga) {
        if (nova == null || antiga == null) {
            return -1;
        }
        if (!obras.contains(antiga)) {
            return 1;
        }
        for(Obra obra: obras){
            if(obra.getISBN().equals(nova.getISBN()) && !obra.equals(antiga)){
                return 2;
            }
        }
        obras.get(obras.indexOf(antiga)).editar(nova);
        return 0;
    }

    public int editarSocio(Socio novo, Socio antigo) {
        if (novo == null || antigo == null) {
            return -1;
        }
        if (!socios.containsKey(antigo.getNumero())) {
            return 1;
        }
        socios.get(antigo.getNumero()).editar(novo);
        return 0;
    }

    public LinkedList<Obra> filtrarObras(String autor, Subgenero subgenero, Genero genero, boolean top) {
        LinkedList<Obra> topRequisitados = top ? (getTopRequisitados().isEmpty() ? obras: getTopRequisitados()): obras;

        if (autor !=null && genero != null && subgenero != null){
            return new LinkedList<>(topRequisitados.stream()
                    .filter(obra -> obra.hasAutor(autor))
                    .filter(obra -> obra.getSubgenero() == subgenero)
                    .filter(obra -> obra.getGenero() == genero)
                    .toList());
        }
        if (autor !=null && genero != null){
            return new LinkedList<>(topRequisitados.stream()
                    .filter(obra -> obra.hasAutor(autor))
                    .filter(obra -> obra.getGenero() == genero)
                    .toList());
        }
        if(autor !=null && subgenero != null){
            return new LinkedList<>(topRequisitados.stream()
                    .filter(obra -> obra.hasAutor(autor))
                    .filter(obra -> obra.getSubgenero() == subgenero)
                    .toList());
        }
        if (genero != null && subgenero!=null){
            return new LinkedList<>(topRequisitados.stream()
                    .filter(obra -> obra.getGenero() == genero)
                    .filter(obra -> obra.getSubgenero() == subgenero)
                    .toList());
        }
        if (autor != null){
            return new LinkedList<>(topRequisitados.stream()
                    .filter(obra -> obra.hasAutor(autor))
                    .toList());
        }
        if (genero != null){
            return new LinkedList<>(topRequisitados.stream()
                    .filter(obra -> obra.getGenero() == genero)
                    .toList());
        }
        if (subgenero != null){
            return new LinkedList<>(topRequisitados.stream()
                    .filter(obra -> obra.getSubgenero() == subgenero)
                    .toList());
        }
        return topRequisitados;
    }

    public LinkedList<Obra> getTopRequisitados() {
        Map<Obra, Integer> requestCountMap = new HashMap<>();

        for (Emprestimo emprestimo : emprestimos) {
            Obra obra = emprestimo.getExemplar().getObra();
            requestCountMap.put(obra, requestCountMap.getOrDefault(obra, 0) + 1);
        }

        List<Map.Entry<Obra, Integer>> sortedEntries = new LinkedList<>(requestCountMap.entrySet());

        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        LinkedList<Obra> topRequisitados = new LinkedList<>();
        for (Map.Entry<Obra, Integer> entry : sortedEntries) {
            topRequisitados.add(entry.getKey());
        }

        return topRequisitados;
    }

    public List<Exemplar> getExemplares(Obra obra) {
        return obra.getExemplares() ;
    }

    public int guardarExemplar(Exemplar exemplar) {
        for (Obra obra : obras){
            for (Exemplar ex : obra.getExemplares()){
                if (exemplar.getCodigo().equals(ex.getCodigo())){
                    return 1;
                }
            }
        }

        return  obras.get(obras.indexOf(exemplar.getObra())).adicionarExemplar(exemplar);
    }

    public int eliminarExemplar(Obra obra, String codigoExemplar) {
        if (obra == null || codigoExemplar == null || !obras.contains(obra)) {
            return -1;
        }

        return obra.eliminarExemplar(codigoExemplar);
    }

    public LinkedList<Emprestimo> getEmprestimosAtrasados() {
        LocalDate today = LocalDate.now();
        return emprestimos.stream()
                .filter(emprestimo -> emprestimo.getDataDevolucaoPrevista().isBefore(today) &&
                        emprestimo.getDataDevolucaoEfetiva() == null)
                .collect(Collectors.toCollection(LinkedList::new));
    }
    public LinkedList<Emprestimo> filtrarEmprestimos(String codigo, int socio, boolean selected) {
        LinkedList<Emprestimo> emprestimosFiltrados = selected ? getEmprestimosAtrasados() : new LinkedList<>(emprestimos);

        Stream<Emprestimo> filteredStream = emprestimosFiltrados.stream();

        if (codigo != null) {
            filteredStream = filteredStream.filter(
                    emprestimo -> emprestimo.getExemplar().getCodigo().equals(codigo));
        }

        if (socio != 0) {
            filteredStream = filteredStream.filter(emprestimo -> emprestimo.getSocio().getNumero() == (socio));
        }

        return new LinkedList<>(filteredStream.toList());
    }

    public HashMap<Integer, Socio> filtrarSocios(boolean selected, int soci, String nom) {
        HashMap<Integer, Socio> sociosFiltrados = selected ? getSociosAtrasados() : new HashMap<>(socios);

        return sociosFiltrados.entrySet().stream()
                .filter(entry -> {
                    Socio socio = entry.getValue();
                    boolean matchId = soci == 0 || socio.getNumero()== soci;
                    boolean matchNom = nom == null || socio.getNome().toLowerCase().contains(nom.toLowerCase());
                    return matchId && matchNom;
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        HashMap::new
                ));
    }

    private HashMap<Integer, Socio> getSociosAtrasados() {
        LocalDate today = LocalDate.now();
        return emprestimos.stream()
                .filter(emprestimo -> emprestimo.getDataDevolucaoPrevista().isBefore(today) &&
                        emprestimo.getDataDevolucaoEfetiva() == null)
                .map(Emprestimo::getSocio)
                .distinct()
                .collect(Collectors.toMap(
                        Socio::getNumero,
                        socio -> socio,
                        (v1, v2) -> v1,
                        HashMap::new
                ));
    }

    public LinkedList<Reserva> filtrarReservas(boolean selected, int soc) {
        Stream<Reserva> filteredStream = reservas.stream();

        if (soc != 0) {
            filteredStream = filteredStream.filter(reserva -> reserva.getSocio().getNumero() == (soc));
        }

        return new LinkedList<>(filteredStream.toList());
    }

    public LinkedList<Obra> filtrarObrasEmprestimo(String titulo) {
        Stream<Obra> filteredStream = obras.stream();
        if (titulo != null) {
            filteredStream = filteredStream.filter(obra-> obra.getTitulo().equals(titulo));
        }
        return new LinkedList<>(filteredStream.toList());
    }





    public int eliminarObra(Obra obra) {
        if (obra == null) {
            return -1;
        }
        if (!obras.contains(obra)) {
            return 1;
        }
        obras.remove(obra);
        return 0;
    }


    public int  adicionarEmprestimo(Exemplar exemplarRequisitar, String numSocio) {

        int numdiasEmprestimo = this.duracaoEmprestimo;

        Socio socio = socios.get(Integer.parseInt(numSocio));
        if (socio == null){
            return -1;
        }
        if (!socio.podeRequisitarLivros(limiteEmprestimosSim)){
            return-2;
        }
        Emprestimo emprestimonovo = new Emprestimo(exemplarRequisitar, socio, numdiasEmprestimo);
        exemplarRequisitar.setDisponivel(false);
        emprestimos.add(emprestimonovo);
        socio.adicionarEmprestimo(emprestimonovo);
        return 0;

    }

    public void adicionarReserva(Obra obra, String numSocio) {
        if(this.reservas == null){
            this.reservas = new LinkedList<Reserva>();
        }

        Socio socio = socios.get(Integer.parseInt(numSocio));
        if (socio == null){
            return;
        }
        Reserva reserva = new Reserva(obra, socio);

        reservas.add(reserva);
    }



    public int realizarDevolucao(Emprestimo emprestimo) {
        if(emprestimo.getDataDevolucaoEfetiva() != null){
            return -1;
        }
        int devolverIndex =emprestimos.indexOf(emprestimo);
        if(devolverIndex < 0){
            return 1;
        }
        emprestimos.get(devolverIndex).realizarDevolucao(multaDiaria);
        Obra obra = emprestimo.getExemplar().getObra();
        int devolver = 0;
        for (Reserva reserva : reservas){
            if(reserva.getObra() == obra && reserva.getSocio().podeRequisitarLivros(limiteEmprestimosSim)){
                devolver = reserva.getSocio().getNumero();
                eliminarReserva(reserva);
            }
        }

        return devolver;
    }

    private void eliminarReserva(Reserva reserva) {

        reservas.remove(reserva);
    }

    public HashMap<Integer, Socio> getSocios() {

        return new HashMap<>(socios);
    }

    public int alertarDevedores() {
        if (socios.isEmpty()){
            return -1;
        }
        int i = 0;
        for (Socio socio : socios.values()) {
            if (socio.getValorEmDivida() != 0) {
                socio.alertarDevedorDivida();
                i++;
            }
            if(!socio.isAnuidadeEmDia()){
                socio.alertarDevedorAnualidade();
                i++;
            }
        }
        return i;
    }


    public int totalRequisicoes() {
        if (emprestimos.isEmpty() || obras.isEmpty()) {
            return 0;
        }
        else{
            int i = 0;
            for (Obra obra : obras) {
                for(Exemplar ex : obra.getExemplares()){
                    if (!ex.isDisponivel()) {
                        i++;
                    }
                }
            }
            return i;
        }
    }

    public int totalDisponiveis() {
        if (emprestimos.isEmpty() || obras.isEmpty()) {
            return 0;
        }
        else{
            int i = 0;
            for (Obra obra : obras) {
                for(Exemplar ex : obra.getExemplares()){
                    if (ex.isDisponivel()) {
                        i++;
                    }
                }
            }
            return i;
        }
    }

    public int today(){
        if (emprestimos.isEmpty() || obras.isEmpty()) {
            return 0;
        }
        else{
            int i = 0;
            for (Emprestimo emprestimo : emprestimos) {
                if (emprestimo.getDataEmprestimo().atStartOfDay().isEqual(LocalDate.now().atStartOfDay())) {
                    i++;
                }
            }
            return i;
        }
    }

    public int week(){
        if (emprestimos.isEmpty() || obras.isEmpty()) {
            return 0;
        }
        else{
            int i = 0;
            for (Emprestimo emprestimo : emprestimos) {
                if (emprestimo.getDataEmprestimo().atStartOfDay().isAfter(LocalDate.now().atStartOfDay().minusDays(7))) {
                    i++;
                }
            }
            return i;
        }
    }

    public int month(){
        if (emprestimos.isEmpty() || obras.isEmpty()) {
            return 0;
        }
        else{
            int i = 0;
            for (Emprestimo emprestimo : emprestimos) {
                if (emprestimo.getDataEmprestimo().atStartOfDay().isAfter(LocalDate.now().atStartOfDay().minusDays(30))) {
                    i++;
                }
            }
            return i;
        }
    }
}