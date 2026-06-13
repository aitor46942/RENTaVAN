package com.example.rentavan.data.network

import com.example.rentavan.data.model.auth.AuthResponse
import com.example.rentavan.data.model.auth.LoginRequest
import com.example.rentavan.data.model.auth.RegisterRequest
import com.example.rentavan.data.model.network.AlquilerBackendRequest
import com.example.rentavan.data.model.network.AlquilerBackendResponse
import com.example.rentavan.data.model.network.CaravanaBackendDTO
import com.example.rentavan.data.model.network.PeriodoDisponibilidadResponse
import com.example.rentavan.data.model.network.ActualizarUsuarioRequest
import com.example.rentavan.data.model.network.UsuarioBackendResponse
import com.example.rentavan.data.model.profile.CaravanaResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RentavanApiService {

    //Usuarios
    @GET("api/usuarios/{id}")
    suspend fun obtenerUsuario(@Path("id") id: Long): Response<UsuarioBackendResponse>

    @PUT("api/usuarios/{id}")
    suspend fun actualizarUsuario(
        @Path("id") id: Long,
        @Body request: ActualizarUsuarioRequest
    ): Response<UsuarioBackendResponse>

    //Auth
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    //Caravanas
    @GET("api/caravanas")
    suspend fun listarCaravanas(): Response<List<CaravanaResponse>>

    @GET("api/caravanas/propietario/{idPropietario}")
    suspend fun listarCaravanasPorPropietario(
        @Path("idPropietario") idPropietario: Long
    ): Response<List<CaravanaResponse>>

    @POST("api/caravanas")
    suspend fun crearCaravana(@Body dto: CaravanaBackendDTO): Response<CaravanaResponse>

    @DELETE("api/caravanas/{idCaravana}")
    suspend fun eliminarCaravana(@Path("idCaravana") idCaravana: Long): Response<Void>

    //Periodos de disponibilidad
    @GET("api/periodos/caravana/{idCaravana}")
    suspend fun listarPeriodos(
        @Path("idCaravana") idCaravana: Long
    ): Response<List<PeriodoDisponibilidadResponse>>

    @POST("api/periodos")
    suspend fun crearPeriodo(@Body dto: PeriodoDisponibilidadResponse): Response<PeriodoDisponibilidadResponse>

    @DELETE("api/periodos/{idPeriodo}")
    suspend fun eliminarPeriodo(@Path("idPeriodo") idPeriodo: Long): Response<Void>

    //Alquileres
    @GET("api/alquileres/cliente/{idCliente}")
    suspend fun listarAlquileresCliente(
        @Path("idCliente") idCliente: Long
    ): Response<List<AlquilerBackendResponse>>

    @POST("api/alquileres")
    suspend fun crearAlquiler(@Body dto: AlquilerBackendRequest): Response<AlquilerBackendResponse>

    @PUT("api/alquileres/{idAlquiler}/cancelar")
    suspend fun cancelarAlquiler(
        @Path("idAlquiler") idAlquiler: Long,
        @Body body: RequestBody
    ): Response<Void>

    @PUT("api/alquileres/{idAlquiler}")
    suspend fun modificarAlquiler(
        @Path("idAlquiler") idAlquiler: Long,
        @Body dto: AlquilerBackendRequest
    ): Response<AlquilerBackendResponse>
}