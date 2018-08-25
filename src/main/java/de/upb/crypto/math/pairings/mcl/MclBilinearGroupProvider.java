package de.upb.crypto.math.pairings.mcl;

import de.upb.crypto.math.factory.BilinearGroup;
import de.upb.crypto.math.factory.BilinearGroupProvider;
import de.upb.crypto.math.factory.BilinearGroupRequirement;

import static de.upb.crypto.math.factory.BilinearGroup.Type.TYPE_3;

public class MclBilinearGroupProvider implements BilinearGroupProvider {

    @Override
    public BilinearGroup provideBilinearGroup(int securityParameter, BilinearGroupRequirement requirements) {
        return new MclBilinearGroup();
    }

    @Override
    public boolean checkRequirements(int securityParameter, BilinearGroupRequirement requirements) {
        return MclBilinearGroup.isAvailable()
                && requirements.getCardinalityNumPrimeFactors() == 1
                && requirements.getType() == TYPE_3
                && securityParameter <= 256
                && !requirements.isHashIntoGTNeeded()
                && !requirements.isHashIntoG2Needed();
    }
}