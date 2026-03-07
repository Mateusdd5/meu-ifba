package com.ifba.meuifba.data.repository

import com.ifba.meuifba.data.local.database.dao.UsuarioDao
import com.ifba.meuifba.data.remote.api.UsuarioApi
import com.ifba.meuifba.data.remote.dto.LoginRequest
import com.ifba.meuifba.data.remote.dto.RegisterRequest
import com.ifba.meuifba.data.remote.dto.UsuarioResponse
import com.ifba.meuifba.data.remote.dto.CursoResponse
import com.ifba.meuifba.domain.model.AreaConhecimentoModel
import com.ifba.meuifba.domain.model.CursoModel
import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepository @Inject constructor(
    private val usuarioDao: UsuarioDao,
    private val usuarioApi: UsuarioApi,
    private val preferencesManager: PreferencesManager
) {

    suspend fun login(email: String, senha: String): Resource<UsuarioModel?> {
        return try {
            val request = LoginRequest(email, senha)
            val response = usuarioApi.login(request)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                preferencesManager.saveLoginData(
                    userId = loginResponse.usuario.id,
                    token = loginResponse.token,
                    rememberMe = true
                )
                Resource.Success(loginResponse.usuario.toModel())
            } else {
                Resource.Error("Email ou senha incorretos")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao fazer login")
        }
    }

    suspend fun register(request: RegisterRequest): Resource<UsuarioModel?> {
        return try {
            val response = usuarioApi.register(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.toModel())
            } else {
                Resource.Error("Erro ao criar conta: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao criar conta")
        }
    }

    suspend fun getCursos(): Resource<List<CursoModel>> {
        return try {
            val response = usuarioApi.getCursos()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.map { it.toModel() })
            } else {
                Resource.Error("Erro ao buscar cursos")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao buscar cursos")
        }
    }

    suspend fun getUsuarioById(usuarioId: Long): UsuarioModel? {
        return try {
            val response = usuarioApi.getUsuarioById(usuarioId)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toModel()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun emailExists(email: String): Boolean {
        return try {
            usuarioDao.getUsuarioByEmail(email) != null
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateUsuario(usuarioId: Long, nome: String, cursoId: Long): Resource<UsuarioModel?> {
        return try {
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao atualizar perfil")
        }
    }

    private fun UsuarioResponse.toModel(): UsuarioModel {
        val iniciais = nome.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .joinToString("") { it.first().uppercase() }

        val dataFormatada = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
            .format(Date(dataCadastro))

        return UsuarioModel(
            id = id,
            nome = nome,
            email = email,
            tipoUsuario = tipoUsuario,
            fotoPerfil = fotoPerfil,
            curso = curso?.toModel(),
            dataCadastro = dataCadastro,
            statusConta = statusConta,
            iniciais = iniciais,
            dataCadastroFormatada = dataFormatada,
            isOrganizador = tipoUsuario == "ORGANIZADOR" || tipoUsuario == "ADMIN",
            isAdmin = tipoUsuario == "ADMIN"
        )
    }

    private fun CursoResponse.toModel(): CursoModel {
        return CursoModel(
            id = id,
            nome = nome,
            area = areaConhecimento?.let {
                AreaConhecimentoModel(
                    id = it.id,
                    nome = it.nome,
                    descricao = it.descricao
                )
            }
        )
    }
}