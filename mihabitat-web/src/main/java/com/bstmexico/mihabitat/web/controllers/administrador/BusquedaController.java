package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;
import com.bstmexico.mihabitat.instalaciones.service.InstalacionService;
import com.bstmexico.mihabitat.proveedores.model.Proveedor;
import com.bstmexico.mihabitat.proveedores.service.ProveedorService;
import com.bstmexico.mihabitat.web.dto.Item;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.Collator;
import java.text.Normalizer;
import java.util.*;

/**
 * Created by Zoé Jonatan Tapia Hernández on 10/08/2015.
 */
@Controller
@RequestMapping(value = "administrador/busqueda")
public class BusquedaController {

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private InstalacionService instalacionService;

    @Autowired
    private ProveedorService proveedorService;

    private static final Logger LOG = LoggerFactory
            .getLogger(BlogController.class);

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "buscar")
    public Collection<Item> buscar(@RequestParam String key, HttpSession session) {
        Collection<Item> items = new ArrayList<Item>();
        Condominio condominio = (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue());

        Set<Contacto> contactos = new HashSet<Contacto>();
        contactos.addAll(contactoService.buscar(condominio, key));

        Set<Departamento> departamentos = new HashSet<Departamento>();
        Map map = new HashMap();
        map.put("condominio", condominio);
        map.put("nombre", key);
        departamentos.addAll(departamentoService.search(map));

        for (Contacto contacto : contactos) {
            if (!CollectionUtils.isEmpty(contacto.getDepartamentos())) {
                for (ContactoDepartamento cd : contacto.getDepartamentos()) {
                    Item item = new Item();
                    item.setLabel(new StringBuilder()
                            .append(!StringUtils.isEmpty(contacto.getNombre()) ? contacto
                                    .getNombre() : "")
                            .append(!StringUtils.isEmpty(contacto
                                    .getApellidoPaterno()) ? " "
                                    + contacto.getApellidoPaterno() : "")
                            .append(!StringUtils.isEmpty(contacto
                                    .getApellidoMaterno()) ? " "
                                    + contacto.getApellidoMaterno() : "")
                            .append(" - ")
                            .append(cd.getDepartamento().getNombre())
                            .toString());
                    item.setUrl("/administrador/estado-cuenta/individual/"
                            + cd.getDepartamento().getId() + "/" + cd.getContacto().getId());
                    item.setId(String.valueOf(contacto.getId()));
                    items.add(item);
                }
            }
        }

        for (Departamento departamento : departamentos) {
            if (!CollectionUtils.isEmpty(departamento.getContactos())) {
                for (ContactoDepartamento cd : departamento.getContactos()) {
                    Item item = new Item();
                    item.setLabel(new StringBuilder()
                            .append(departamento.getNombre())
                            .append(" - ")
                            .append(!StringUtils.isEmpty(cd.getContacto()
                                    .getNombre()) ? cd.getContacto()
                                    .getNombre() : "")
                            .append(!StringUtils.isEmpty(cd.getContacto()
                                    .getApellidoPaterno()) ? " "
                                    + cd.getContacto().getApellidoPaterno()
                                    : "")
                            .append(!StringUtils.isEmpty(cd.getContacto()
                                    .getApellidoMaterno()) ? " "
                                    + cd.getContacto().getApellidoMaterno()
                                    : "").toString());
                    item.setUrl("/administrador/estado-cuenta/individual/"
                            + departamento.getId() + "/" + cd.getContacto().getId());
                    item.setId(String.valueOf(cd.getContacto().getId()));
                    items.add(item);
                }
            }
        }

        Set<Instalacion> instalaciones = new HashSet<>();
        instalaciones.addAll(instalacionService.getList(condominio));

        for(Instalacion ins : instalaciones) {
            if(removeAcentoMarks(ins.getNombre().toLowerCase()).contains(removeAcentoMarks(key.toLowerCase()))) {
                Item item = new Item();
                item.setLabel(ins.getNombre() + " | Instalación");
                item.setUrl("/administrador/instalaciones/reservar/"
                        + ins.getId());
                item.setId(String.valueOf(ins.getId()));
                items.add(item);
            }
        }

        Set<Proveedor> proveedores = new HashSet<>();
        proveedores.addAll(proveedorService.search(map));

        for(Proveedor prov : proveedores) {
            if(removeAcentoMarks(prov.getNombre().toLowerCase()).contains(removeAcentoMarks(key.toLowerCase()))) {
                Item item = new Item();
                item.setLabel(prov.getNombre() + " | Proveedor");
                item.setUrl("/administrador/proveedores/actualizar/"
                        + prov.getId());
                item.setId(String.valueOf(prov.getId()));
                items.add(item);
            }
        }

        return items;
    }

    private static String removeAcentoMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
