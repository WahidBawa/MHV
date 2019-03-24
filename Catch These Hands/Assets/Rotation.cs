using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Rotation : MonoBehaviour
{
    Vector3 palm;
    public double angle;
    // Start is called before the first frame update
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {
        GameObject leftHand = GameObject.Find("RigidRoundHand_L");
        Vector3 rightHand = this.transform.Find("palm").position;
        if (leftHand != null)
        {
            float dx = (leftHand.transform.Find("palm").position.x + rightHand.x) / 2.0f;
            float dz = (leftHand.transform.Find("palm").position.z + rightHand.z) / 2.0f;
            angle = (Mathf.Rad2Deg*(Mathf.Atan2(dz, dx)) - 90) / 20;
            Debug.Log("X: " + dx + " Z: " + dz + " ANGLE: " + angle);

        }
        else
        {
            angle = 0;
        }
    }
}
