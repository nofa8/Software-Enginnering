package Modelos;

public enum Distrito {
    AVEIRO,
    BEJA,
    BRAGA,
    BRAGANCA,
    CASTELO_BRANCO,
    COIMBRA,
    EVORA,
    FARO,
    GUARDA,
    LEIRIA,
    LISBOA,
    PORTALEGRE,
    PORTO,
    SANTAREM,
    SETUBAL,
    VIANA_DO_CASTELO,
    VILA_REAL,
    VISEU,
    ACORES,
    MADEIRA;

    @Override
    public String toString() {
        // Formata o nome do distrito para que tenha a primeira letra em maiúscula e o restante em minúsculas
        String formatted = name().toLowerCase().replace('_', ' ');
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
    }

    public static Distrito fromFormattedString(String formattedString) {
        // Converte a string formatada para o formato utilizado na enumeração
        String enumName = formattedString.toUpperCase().replace(' ', '_');
        return Distrito.valueOf(enumName);
    }
}