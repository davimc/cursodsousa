package io.github.cursodsousa.mscartoes.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Bandeira {
    MASTERCARD(1,"Mastercard"), VISA(2, "Visa");

    @Getter
    private int cod;
    @Getter
    private String desc;

    public static Bandeira toEnum(Integer cod) {
        if(cod == null) return null;
        for(Bandeira l: Bandeira.values())
            if(cod.equals(l.getCod()))
                return l;
        throw new IllegalArgumentException("Código inválido");
    }
    public static Bandeira toEnum(String desc) {
        if(desc == null) return null;
        for(Bandeira l: Bandeira.values())
            if(desc.equals(l.getDesc()))
                return l;
        throw new IllegalArgumentException("Descrição inválida");
    }
}
