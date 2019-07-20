package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner
{
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService)
    {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception
    {
        int count = petTypeService.findAll().size();
        if(count == 0)
            loadData();
    }

    private void loadData()
    {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        System.out.println("Pet Types Loaded......");

        Owner owner1 = new Owner();
        owner1.setFirstName("Sam");
        owner1.setLastName("Winchester");
        owner1.setAddress("22 Street");
        owner1.setCity("Miami");
        owner1.setTelephone("12376576");

        Pet samsPet = new Pet();
        samsPet.setPetType(dog);
        samsPet.setOwner(owner1);
        samsPet.setBirthDate(LocalDate.now());
        samsPet.setName("Tommy");

        owner1.getPets().add(samsPet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Dean");
        owner2.setLastName("Samuel");
        owner2.setAddress("22 Street");
        owner2.setCity("Miami");
        owner2.setTelephone("12376576");

        Pet deansPet = new Pet();
        deansPet.setPetType(cat);
        deansPet.setOwner(owner2);
        deansPet.setBirthDate(LocalDate.now());
        deansPet.setName("Cosco");

        owner2.getPets().add(deansPet);

        ownerService.save(owner2);

        System.out.println("Owners Loaded.......");

        Visit catVisit = new Visit();
        catVisit.setPet(deansPet);
        catVisit.setDescription("Sneezy Kitty");
        catVisit.setDate(LocalDate.now());

        visitService.save(catVisit);

        System.out.println("Visit Loaded.....");

        Speciality radiology = new Speciality();
        radiology.setDescription("radiology");

        specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("surgery");

        specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentistry");

        specialityService.save(dentistry);

        System.out.println("Specialties Loaded......");

        Vet vet1 = new Vet();
        vet1.setFirstName("Morris");
        vet1.setLastName("Mano");
        vet1.getSpecialities().add(surgery);
        vet1.getSpecialities().add(dentistry);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Emma");
        vet2.setLastName("Watson");
        vet2.getSpecialities().add(radiology);

        vetService.save(vet2);

        System.out.println("Vet Loaded......");
    }
}
