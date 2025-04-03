package edu.upc.dsa.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.VuelosManagerImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/vuelos", description = "Operaciones sobre vuelos")
@Path("/vuelos")
public class VuelosService {

    private VuelosManagerImpl vm;

    public VuelosService() {
        this.vm = VuelosManagerImpl.getInstance();
        if (vm.sizeVuelos() == 0) {

            vm.addAvion("AAA1", "Vueling", "Boeing 737");
            vm.addAvion("AAA2", "Ryanair", "Boeing 747");
            vm.addAvion("AAA3", "EasyJet", "Boeing 767");
            // Añadir algunos vuelos de ejemplo
            vm.addVuelo("VUELO1", "10:00", "12:00", vm.getAvion("AAA1"), "Barcelona", "Madrid");
            vm.addVuelo("VUELO2", "13:00", "23:00", vm.getAvion("AAA2"), "China", "Francia");
            vm.addVuelo("VUELO3", "16:00", "00:00", vm.getAvion("AAA3"), "Senegal", "Australia");
        }
    }

    @GET
    @ApiOperation(value = "conseguir todos los Vuelos", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Vuelo.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks() {

        List<Vuelo> vuelos = this.vm.getVuelos();

        GenericEntity<List<Vuelo>> entity = new GenericEntity<List<Vuelo>>(vuelos) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "Conseguir un Vuelo", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Vuelo.class),
            @ApiResponse(code = 404, message = "Vuelo no encontrado")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVuelo(@PathParam("id") String id) {
        Vuelo t = this.vm.getVuelo(id);
        if (t == null) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }

    @DELETE
    @ApiOperation(value = "eliminar un Vuelo", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Vuelo no encontrado")
    })
    @Path("/{id}")
    public Response deleteVuelo(@PathParam("id") String id) {
        Vuelo t = this.vm.getVuelo(id);
        if (t == null) return Response.status(404).build();
        else this.vm.deleteVuelo(id);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "actualizar un Vuelo", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Vuelo no encontrado")
    })
    @Path("/")
    public Response updateTrack(Vuelo vuelo) {

        Vuelo t = this.vm.updateVuelo(vuelo);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }



    @POST
    @ApiOperation(value = "crear un nuevo Vuelo", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Vuelo.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTrack(Vuelo vuelo) {

        if (vuelo.getIdVuelo()==null || vuelo.getHoraSalida()==null || vuelo.getHoraLlegada()==null || vuelo.getOrigen()==null || vuelo.getDestino()==null)  return Response.status(500).entity(vuelo).build();
        this.vm.addVuelo(vuelo);
        return Response.status(201).entity(vuelo).build();
    }

}
