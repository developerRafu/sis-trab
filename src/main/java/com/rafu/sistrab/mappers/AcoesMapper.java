package com.rafu.sistrab.mappers;

import com.rafu.sistrab.rest.dto.AcoesRequest;
import com.rafu.sistrab.vo.Acao;
import com.rafu.sistrab.vo.enums.Risco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcoesMapper {
    Acao toAcao(AcoesRequest acoesRequest);
    default Risco toRisco(Integer risco){
        return Risco.from(risco);
    }
}
