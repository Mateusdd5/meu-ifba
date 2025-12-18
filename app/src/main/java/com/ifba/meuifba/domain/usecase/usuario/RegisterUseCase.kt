package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.utils.Resource
import com.ifba.meuifba.utils.ValidationUtils
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    // private val usuarioRepository: UsuarioRepository
) {

    suspend operator fun invoke(
        nome: String,
        email: String,
        senha: String,
        confirmarSenha: String,
        cursoId: Long
    ): Resource<UsuarioModel> {
        TODO("Implementar quando UsuarioRepository estiver pronto")
    }

    /*
    // Implementação futura:
    suspend operator fun invoke(
        nome: String,
        email: String,
        senha: String,
        confirmarSenha: String,
        cursoId: Long
    ): Resource<UsuarioModel> {
        return try {
            // Validações
            if (!ValidationUtils.isNotEmpty(nome)) {
                return Resource.Error("Nome é obrigatório")
            }

            if (!ValidationUtils.hasMinLength(nome, 3)) {
                return Resource.Error("Nome deve ter no mínimo 3 caracteres")
            }

            if (!ValidationUtils.isValidEmail(email)) {
                return Resource.Error("E-mail inválido")
            }

            if (!ValidationUtils.isIfbaEmail(email)) {
                return Resource.Error("Use seu e-mail institucional @ifba.edu.br")
            }

            if (!ValidationUtils.isValidPassword(senha)) {
                return Resource.Error("Senha deve ter entre 6 e 32 caracteres")
            }

            val passwordStrength = ValidationUtils.getPasswordStrength(senha)
            if (passwordStrength == PasswordStrength.WEAK) {
                return Resource.Error("Senha muito fraca. Use letras, números e caracteres especiais")
            }

            if (!ValidationUtils.passwordsMatch(senha, confirmarSenha)) {
                return Resource.Error("Senhas não conferem")
            }

            // Verificar se email já existe
            val emailExiste = usuarioRepository.emailExists(email)
            if (emailExiste) {
                return Resource.Error("E-mail já cadastrado")
            }

            // Criar usuário
            val usuario = usuarioRepository.register(
                nome = nome,
                email = email,
                senha = senha, // Será hasheada no Repository
                cursoId = cursoId
            )

            Resource.Success(usuario)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao criar conta")
        }
    }
    */
}