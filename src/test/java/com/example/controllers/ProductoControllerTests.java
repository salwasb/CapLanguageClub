package com.example.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.entities.Asistente;
import com.example.entities.Conversacion;
import com.example.entities.Idioma;
import com.example.entities.Modo;
import com.example.entities.Nivel;
import com.example.service.AsistenteService;
import com.example.service.ConversacionService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc // Test a los controladores, a los end points, teniendo Spring Securiy
                      // configurado
// @ContextConfiguration(classes = SecurityConfig.class)
// @WebAppConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
// @WithMockUser(username = "vrmachado@gmail.com",
// authorities = {"ADMIN", "USER"})
// @WithMockUser(roles="ADMIN") - Error 403
public class ProductoControllerTests {

    @Autowired
    private MockMvc mockMvc; // Simular peticiones HTTP

    // Permite agregar objetos simulados al contexto de la aplicacion.
    // El simulacro o simulacion va a remplazar cualquier bean existente
    // en el contexto de la aplicacion.
    @MockBean
    private AsistenteService asistenteService;

    @MockBean
    private ConversacionService conversacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FileUploadUtil fileUploadUtil;

    @MockBean
    private FileDownloadUtil fileDownloadUtil;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Test de intento de guardar un producto sin autorizacion")
    void testGuardarAsistente() throws Exception {

         List<Conversacion> conversacionesAsistente1 = new ArrayList<>();
         List<Asistente> asistenteconversaciones1 = new ArrayList<>();

        Conversacion conversacion1 = Conversacion.builder()
                                        .id(1)
                                        .titulo("francais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 20))
                                        .hora(LocalTime.of(20, 30, 20))
                                        .modo(Modo.ONLINE)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.FRANCES)
                                        .nivel(Nivel.A)
                                        .asistentes(asistenteconversaciones1)
                                        .numeroAsistentes(4)
                                        .build();
             conversacionesAsistente1.add(conversacion1);
             conversacionService.save(conversacion1);

        Asistente asistente1 = Asistente.builder()
                .id(1)
                .nombre("Nawal")
                .apellidos("AAA")
                .imagenAsistente("test.jpeg")
                .correo("jgjfj@cap.com")
                .idioma(Idioma.FRANCES)
                .conversacion(conversacionesAsistente1)
                .build();

        asistenteconversaciones1.add(asistente1);

        String jsonStringProduct = objectMapper.writeValueAsString(asistente1);

        MockMultipartFile bytesArrayProduct = new MockMultipartFile("asistente",
                null, "application/json", jsonStringProduct.getBytes());

        // multipart: perfoms a POST request
        mockMvc.perform(multipart("/asistentes")
                .file("file", null)
                .file(bytesArrayProduct))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @DisplayName("Test guardar producto con usuario mockeado")
    @Test
    @WithMockUser(username = "vrmachado@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
    void testGuardarProductoConUserMocked() throws Exception {
        // given
        Presentacion presentacion = Presentacion.builder()
                .description(null)
                .name("docena")
                .build();

        Producto producto = Producto.builder()
                .name("Camara")
                .description("Resolucion Alta")
                .price(2000.00)
                .stock(40)
                .presentacion(presentacion)
                .imagenProducto("perro.jpeg")
                .build();

        given(productoService.save(any(Producto.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
        // when
        String jsonStringProduct = objectMapper.writeValueAsString(producto);

        MockMultipartFile bytesArrayProduct = new MockMultipartFile("producto",
                null, "application/json", jsonStringProduct.getBytes());

        ResultActions response = mockMvc.perform(multipart("/productos")
                .file("file", null)
                .file(bytesArrayProduct));
        // then

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.producto.name", is(producto.getName())))
                .andExpect(jsonPath("$.producto.description", is(producto.getDescription())));
    }

    @WithMockUser(username = "vrmachado@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
    public void testListarProductos() throws Exception {

        // given

        List<Producto> productos = new ArrayList<>();

        Presentacion presentacion = Presentacion.builder()
                .description(null)
                .name("docena")
                .build();

        Producto producto = Producto.builder()
                .name("Camara")
                .description("Resolucion Alta")
                .price(2000.00)
                .stock(40)
                .presentacion(presentacion)
                .imagenProducto("perro.jpeg")
                .build();

        Presentacion presentacion1 = Presentacion.builder()
                .description(null)
                .name("unidad")
                .build();

        Producto producto1 = Producto.builder()
                .name("Pixel 7")
                .description("Google Pixel Phone")
                .price(2000.00)
                .stock(40)
                .presentacion(presentacion1)
                .imagenProducto("perro.jpeg")
                .build();

        productos.add(producto);
        productos.add(producto1);

        given(productoService.findAll(Sort.by("name")))
                .willReturn(productos);

        // when

        ResultActions response = mockMvc.perform(get("/productos"));

        // then

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(productos.size())));

    }

    // Test. Recuperar un producto por el id
    @Test
    @WithMockUser(username = "vrmachado@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
    public void testRecuperarProductoPorId() throws Exception {
        // given
        int productoId = 1;

        Presentacion presentacion = Presentacion.builder()
                .description(null)
                .name("docena")
                .build();

        Producto producto = Producto.builder()
                .name("Camara")
                .description("Resolucion Alta")
                .price(2000.00)
                .stock(40)
                .presentacion(presentacion)
                .imagenProducto("perro.jpeg")
                .build();

        given(productoService.findById(productoId)).willReturn(producto);

        // when

        ResultActions response = mockMvc.perform(get("/productos/{id}", productoId));

        // then

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.producto.name", is(producto.getName())));
    }

    // Test. Producto no encontrado
    @Test
    @WithMockUser(username = "vrmachado@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
    public void testProductoNoEncontrado() throws Exception {
        // given
        int productoId = 1;

        given(productoService.findById(productoId)).willReturn(null);

        // when

        ResultActions response = mockMvc.perform(get("/productos/{id}", productoId));

        // then

        response.andExpect(status().isNotFound());

    }

    // Test. Actualizar un producto
    @Test
    @WithMockUser(username = "vrmachado@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
    public void testActualizarProducto() throws Exception {

        // given

        int productoId = 1;

        Presentacion presentacionGuardada = Presentacion.builder()
                .description(null)
                .name("docena")
                .build();

        Producto productoGuardado = Producto.builder()
                .name("Camara")
                .description("Resolucion Alta")
                .price(2000.00)
                .stock(40)
                .presentacion(presentacionGuardada)
                .imagenProducto("perro.jpeg")
                .build();

        Presentacion presentacionActualizada = Presentacion.builder()
                .description(null)
                .name("unidad")
                .build();

        Producto productoActualizado = Producto.builder()
                .name("HDCamara")
                .description("Muy Alta Resolucion")
                .price(2500.00)
                .stock(400)
                .presentacion(presentacionActualizada)
                .imagenProducto("perro.jpeg")
                .build();

        given(productoService.findById(productoId)).willReturn(productoGuardado)
                .willReturn(productoGuardado);
        given(productoService.save(any(Producto.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when

        // Si todo el producto se recibe en el cuerpo de la peticion procedemos
        // de la forma siguiente, de lo contrario, si por una parte va el producto
        // y por otra la imagen, hay que proceder de manera diferente (muy similar
        // al test de persistir un producto con su imagen)

        ResultActions response = mockMvc.perform(put("/productos/{id}", productoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoActualizado)));

        // then

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.producto.name", is(productoActualizado.getName())));
    }

    // Test. Eliminar un producto
    @Test
    @WithMockUser(username = "vrmachado@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
    public void testEliminarProducto() throws Exception {

        // given

        int productoId = 1;

        Presentacion presentacion = Presentacion.builder()
                .description(null)
                .name("docena")
                .build();

        Producto producto = Producto.builder()
                .name("Camara")
                .description("Resolucion Alta")
                .price(2000.00)
                .stock(40)
                .presentacion(presentacion)
                .imagenProducto("perro.jpeg")
                .build();

        given(productoService.findById(productoId))
                .willReturn(producto);

        willDoNothing().given(productoService).delete(producto);

        // when

        ResultActions response = mockMvc.perform(delete("/productos/{id}", productoId));

        // then

        response.andExpect(status().isOk())
                .andDo(print());

    }

}
