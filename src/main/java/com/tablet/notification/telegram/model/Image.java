package com.tablet.notification.telegram.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "spring_tablet_image")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @SequenceGenerator(name = "image_seq", sequenceName = "image_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq")
    private int id;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_type")
    @Enumerated(EnumType.STRING)
    private ImageType type;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "animal_type")
    @Enumerated(EnumType.STRING)
    private AnimalType animalType;

//    @Column(name = "image_data")
//    @Lob
//    private byte[] data;

    public Image(String imageName, ImageType type, String imageUrl, AnimalType animalType) {
        this.imageName = imageName;
        this.type = type;
        this.imageUrl = imageUrl;
        this.animalType = animalType;
    }
}



