package net.mcreator.xillysorespawn.entity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.SpiderRobotRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class SpiderRobotEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(3.25F,2.25F)).build("spider_robot").setRegistryName("spider_robot");
    public SpiderRobotEntity(XillysOrespawnModElements instance){super(instance,74);FMLJavaModLoadingContext.get().getModEventBus().register(new SpiderRobotRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("spider_robot_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,SpiderRobotEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||((false));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnCreatureBase {
        private int localTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 750; this.setCanPickUpLoot(false);
            this.setNoGravity(true);
            this.stepHeight=1.1F;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 1500D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 100D)
                .createMutableAttribute(Attributes.ARMOR, 16D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(6,new LookAtGoal(this,PlayerEntity.class,12));goalSelector.addGoal(7,new LookRandomlyGoal(this));}
        @Override public boolean attackEntityAsMob(Entity e){boolean h=e.attackEntityFrom(DamageSource.causeMobDamage(this),100);if(h)OreSpawnEntityBase.knockAway(this,e,1.2,.15);return h;}
        @Override public boolean attackEntityFrom(DamageSource s,float a){if(s==DamageSource.IN_WALL||s==DamageSource.CACTUS||s==DamageSource.IN_FIRE||s==DamageSource.ON_FIRE||s==DamageSource.MAGIC||s==DamageSource.STARVE)return false;return super.attackEntityFrom(s,a);}
        @Override public boolean canPassengerSteer(){
            Entity rider=getControllingPassenger();
            return !world.isRemote || rider instanceof PlayerEntity && ((PlayerEntity)rider).isUser();
        }
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand hand){ItemStack s=p.getHeldItem(hand);if(s.getItem()==Items.DIAMOND&&getDistanceSq(p)<25){if(!world.isRemote)heal(Math.min(100,getMaxHealth()-getHealth()));if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(!world.isRemote&&getPassengers().isEmpty()&&getDistanceSq(p)<16)p.startRiding(this);return ActionResultType.func_233537_a_(world.isRemote);}
        private static final double IDLE_CLEARANCE=1.55D;
        private static final double RIDDEN_CLEARANCE=4.25D;

        /** Surface beneath the body/leading edge, not a renderer-only offset. */
        private double supportHeight(double x,double z) {
            int bx=MathHelper.floor(x),bz=MathHelper.floor(z);
            int top=Math.min(255,MathHelper.floor(getPosY()));
            int bottom=Math.max(0,top-12);
            for(int y=top;y>=bottom;y--) {
                BlockPos p=new BlockPos(bx,y,bz);
                if(!world.isBlockLoaded(p))return Double.NEGATIVE_INFINITY;
                VoxelShape shape=world.getBlockState(p).getCollisionShape(world,p);
                if(!shape.isEmpty())return y+shape.getEnd(Direction.Axis.Y);
            }
            return Double.NEGATIVE_INFINITY;
        }

        private double supportHeightAhead(double vx,double vz) {
            // Sample under the hull and in front of it so steps lift the body
            // before its collision box reaches them. No teleport/noClip is used.
            double support=supportHeight(getPosX(),getPosZ());
            double radius=getWidth()*0.45D;
            double leadX=MathHelper.clamp(vx*4,-1.5D,1.5D);
            double leadZ=MathHelper.clamp(vz*4,-1.5D,1.5D);
            for(int x=-1;x<=1;x+=2)for(int z=-1;z<=1;z+=2)
                support=Math.max(support,supportHeight(getPosX()+x*radius+leadX,getPosZ()+z*radius+leadZ));
            return support;
        }

        @Override public void travel(Vector3d input) {
            Entity passenger=getControllingPassenger();
            if(passenger instanceof LivingEntity) {
                LivingEntity rider=(LivingEntity)passenger;
                rotationYaw=rider.rotationYaw;rotationPitch=0;
                renderYawOffset=rotationYaw;rotationYawHead=rotationYaw;
                getNavigator().clearPath();
                input=new Vector3d(rider.moveStrafing*0.5D,0,rider.moveForward);
            }
            // Same movement authority as vanilla mounts: only the controlling
            // player's client predicts riding, server moves an unoccupied robot.
            if(!canPassengerSteer()) {
                setMotion(Vector3d.ZERO);
                return;
            }
            double speed=passenger!=null?(input.z<0?0.25D:0.45D):getAttributeValue(Attributes.MOVEMENT_SPEED);
            Vector3d control=new Vector3d(input.x,0,input.z);
            if(control.lengthSquared()>1)control=control.normalize();
            double yaw=Math.toRadians(rotationYaw);
            double desiredX=(control.x*Math.cos(yaw)-control.z*Math.sin(yaw))*speed;
            double desiredZ=(control.z*Math.cos(yaw)+control.x*Math.sin(yaw))*speed;
            Vector3d previous=getMotion();
            double vx=previous.x+(desiredX-previous.x)*0.25D;
            double vz=previous.z+(desiredZ-previous.z)*0.25D;
            double support=supportHeightAhead(vx,vz);
            double clearance=passenger!=null?RIDDEN_CLEARANCE:IDLE_CLEARANCE;
            double targetY=Double.isFinite(support)
                ?MathHelper.clamp((support+clearance-getPosY())*0.2D,-0.45D,0.45D):-0.45D;
            double vy=previous.y+(targetY-previous.y)*0.3D;
            setMotion(vx,vy,vz);
            move(MoverType.SELF,getMotion());
            fallDistance=0;
            func_233629_a_(this,false);
        }
        @Override public boolean onLivingFall(float distance,float multiplier){return false;}
        @Override public double getMountedYOffset(){return 2.625D;}
        @Override public void updatePassenger(Entity passenger){
            if(!getPassengers().contains(passenger))return;
            double yaw=Math.toRadians(rotationYaw);
            passenger.setPosition(getPosX()+3*Math.sin(yaw),getPosY()+getMountedYOffset()+passenger.getYOffset(),getPosZ()-3*Math.cos(yaw));
        }
        @Nullable @Override public Entity getControllingPassenger(){return getPassengers().isEmpty()?null:getPassengers().get(0);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("robotspider",SoundEvents.ENTITY_IRON_GOLEM_STEP);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){OreSpawnLogic.drop(world,this,Items.IRON_INGOT,16+rand.nextInt(16),6);OreSpawnLogic.drop(world,this,Items.REDSTONE,12+rand.nextInt(16),6);}
        private int didonce;
        @Override public void livingTick(){super.livingTick();if(world.isRemote)updateLegs();}
    private void initLegData() {
for (int i = 0; i < 8; ++i) {
            this.spiderRobotInfo.ycurrentangle[i] = 0.0f;
            this.spiderRobotInfo.ywantedangle[i] = 0.0f;
            this.spiderRobotInfo.ydisplayangle[i] = 0.0f;
            this.spiderRobotInfo.yvelocity[i] = 0.0f;
            this.spiderRobotInfo.ymid[i] = 0.0f;
            this.spiderRobotInfo.yoff[i] = 0.0f;
            this.spiderRobotInfo.yrange[i] = 0.0f;
            this.spiderRobotInfo.udcurrentangle[i] = 0.0f;
            this.spiderRobotInfo.udwantedangle[i] = 0.0f;
            this.spiderRobotInfo.uddisplayangle[i] = 0.0f;
            this.spiderRobotInfo.udvelocity[i] = 0.0f;
            this.spiderRobotInfo.p1xangle[i] = 0.7853981633974483;
            this.spiderRobotInfo.p2xangle[i] = 0.0;
            this.spiderRobotInfo.p3xangle[i] = -0.7853981633974483;
            this.spiderRobotInfo.pxvelocity[i] = 0.0f;
            this.spiderRobotInfo.foot_xpos[i] = (float)this.getPosX();
            this.spiderRobotInfo.foot_ypos[i] = (float)this.getPosY();
            this.spiderRobotInfo.foot_zpos[i] = (float)this.getPosZ();
            this.spiderRobotInfo.realposx[i] = 0.0f;
            this.spiderRobotInfo.realposy[i] = 0.0f;
            this.spiderRobotInfo.realposz[i] = 0.0f;
            this.spiderRobotInfo.legoff[i] = 0.0f;
            this.spiderRobotInfo.footup[i] = 1;
            this.spiderRobotInfo.uppoint[i] = 0.0f;
            this.spiderRobotInfo.footingticker[i] = 0;
            this.spiderRobotInfo.gpcounter = 0;
            if (i == 0) {
                this.spiderRobotInfo.legoff[i] = 1.25f;
                this.spiderRobotInfo.ymid[i] = -0.32f;
                this.spiderRobotInfo.yrange[i] = 0.2617994f;
                this.spiderRobotInfo.pairedwith[i] = 1;
                this.spiderRobotInfo.yoff[i] = -0.3f;
            }
            if (i == 1) {
                this.spiderRobotInfo.legoff[i] = 1.25f;
                this.spiderRobotInfo.ymid[i] = 3.4615927f;
                this.spiderRobotInfo.yrange[i] = -0.2617994f;
                this.spiderRobotInfo.pairedwith[i] = 0;
                this.spiderRobotInfo.yoff[i] = -0.3f;
            }
            if (i == 2) {
                this.spiderRobotInfo.legoff[i] = 2.0f;
                this.spiderRobotInfo.ymid[i] = -1.0f;
                this.spiderRobotInfo.yrange[i] = 0.2617994f;
                this.spiderRobotInfo.pairedwith[i] = 3;
                this.spiderRobotInfo.yoff[i] = -0.1f;
            }
            if (i == 3) {
                this.spiderRobotInfo.legoff[i] = 2.0f;
                this.spiderRobotInfo.ymid[i] = 4.1415925f;
                this.spiderRobotInfo.yrange[i] = -0.2617994f;
                this.spiderRobotInfo.pairedwith[i] = 2;
                this.spiderRobotInfo.yoff[i] = -0.1f;
            }
            if (i == 4) {
                this.spiderRobotInfo.legoff[i] = 1.75f;
                this.spiderRobotInfo.ymid[i] = 0.62831855f;
                this.spiderRobotInfo.yrange[i] = 0.2617994f;
                this.spiderRobotInfo.pairedwith[i] = 5;
                this.spiderRobotInfo.yoff[i] = -0.3f;
            }
            if (i == 5) {
                this.spiderRobotInfo.legoff[i] = 1.75f;
                this.spiderRobotInfo.ymid[i] = 2.5132742f;
                this.spiderRobotInfo.yrange[i] = -0.2617994f;
                this.spiderRobotInfo.pairedwith[i] = 4;
                this.spiderRobotInfo.yoff[i] = -0.3f;
            }
            if (i == 6) {
                this.spiderRobotInfo.legoff[i] = 3.4f;
                this.spiderRobotInfo.ymid[i] = 1.05f;
                this.spiderRobotInfo.yrange[i] = 0.2617994f;
                this.spiderRobotInfo.pairedwith[i] = 7;
                this.spiderRobotInfo.yoff[i] = -0.1f;
            }
            if (i != 7) continue;
            this.spiderRobotInfo.legoff[i] = 3.4f;
            this.spiderRobotInfo.ymid[i] = 2.0915928f;
            this.spiderRobotInfo.yrange[i] = -0.2617994f;
            this.spiderRobotInfo.pairedwith[i] = 6;
            this.spiderRobotInfo.yoff[i] = -0.1f;
        }
    }

    private float getNewVelocity(float v, float diff, float curval) {
        float tv = v;
        if ((tv *= 8.0f) < 1.0f) {
            tv = 1.0f;
        }
        if (tv > 4.0f) {
            tv = 4.0f;
        }
        if (diff > 0.0f) {
            if ((double)diff < Math.PI / 360 * (double)tv) {
                curval = 0.0f;
            } else {
                curval = (float)((double)curval + 0.004363323129985824 * (double)tv);
                if ((double)diff < 0.06981317007977318 * (double)tv) {
                    curval = (float)(Math.PI / 180 * (double)tv);
                }
                if ((double)diff < Math.PI / 90 * (double)tv) {
                    curval = (float)(Math.PI / 360 * (double)tv);
                }
                if ((double)curval > 0.06981317007977318 * (double)tv) {
                    curval = (float)(0.06981317007977318 * (double)tv);
                }
            }
        } else if ((double)diff > -Math.PI / 360 * (double)tv) {
            curval = 0.0f;
        } else {
            curval = (float)((double)curval - 0.004363323129985824 * (double)tv);
            if ((double)diff > -0.06981317007977318 * (double)tv) {
                curval = -((float)(Math.PI / 180 * (double)tv));
            }
            if ((double)diff > -Math.PI / 90 * (double)tv) {
                curval = -((float)(Math.PI / 360 * (double)tv));
            }
            if ((double)curval < -0.06981317007977318 * (double)tv) {
                curval = -((float)(0.06981317007977318 * (double)tv));
            }
        }
        return curval;
    }

    public void updateLegs() {
        if (!this.world.isRemote) {
            return;
        }
        this.rotationYaw %= 360.0f;
        while (this.rotationYaw < 0.0f) {
            this.rotationYaw += 360.0f;
        }
        ++this.spiderRobotInfo.gpcounter;
        if (this.didonce == 0) {
            this.didonce = 1;
            this.initLegData();
        }
        float d1 = (float)(this.prevPosX - this.getPosX());
        float d2 = (float)(this.prevPosY - this.getPosY());
        float d3 = (float)(this.prevPosZ - this.getPosZ());
        float realv = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
        int i = 0;
        for (i = 0; i < 8; ++i) {
            double rdv;
            int fcount = 0;
            int n = i;
            this.spiderRobotInfo.footingticker[n] = this.spiderRobotInfo.footingticker[n] + 1;
            this.spiderRobotInfo.realposx[i] = (float)(this.getPosX() - (double)this.spiderRobotInfo.legoff[i] * Math.sin(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.spiderRobotInfo.ymid[i]));
            this.spiderRobotInfo.realposz[i] = (float)(this.getPosZ() + (double)this.spiderRobotInfo.legoff[i] * Math.cos(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.spiderRobotInfo.ymid[i]));
            this.spiderRobotInfo.realposy[i] = (float)this.getPosY() + this.spiderRobotInfo.yoff[i];
            int it = this.spiderRobotInfo.footingticker[i] + this.spiderRobotInfo.footingticker[this.spiderRobotInfo.pairedwith[i]];
            if (it > 50 && this.spiderRobotInfo.footingticker[i] > this.spiderRobotInfo.footingticker[this.spiderRobotInfo.pairedwith[i]]) {
                this.spiderRobotInfo.footingticker[i] = 0;
            }
            d1 = this.spiderRobotInfo.realposx[i] - this.spiderRobotInfo.foot_xpos[i];
            d2 = this.spiderRobotInfo.realposy[i] - this.spiderRobotInfo.foot_ypos[i];
            d3 = this.spiderRobotInfo.realposz[i] - this.spiderRobotInfo.foot_zpos[i];
            float dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            dd *= 16.0f;
            float da = (float)(Math.abs((double)this.spiderRobotInfo.ycurrentangle[i] - (Math.toRadians(MathHelper.wrapDegrees((double)this.rotationYaw)) + (double)this.spiderRobotInfo.ymid[i])) % (Math.PI * 2));
            if ((double)da > Math.PI) {
                da = (float)((double)da - Math.PI * 2);
            }
            if ((double)da < -Math.PI) {
                da = (float)((double)da + Math.PI * 2);
            }
            da = Math.abs(da);
            if (dd > 294.0f || dd < 32.0f || da > Math.abs(this.spiderRobotInfo.yrange[i]) * 8.0f / 7.0f || (double)Math.abs(this.spiderRobotInfo.udcurrentangle[i]) > 1.25 || this.spiderRobotInfo.footingticker[i] == 0) {
                this.findNewFooting(i);
                d1 = this.spiderRobotInfo.realposx[i] - this.spiderRobotInfo.foot_xpos[i];
                d2 = this.spiderRobotInfo.realposy[i] - this.spiderRobotInfo.foot_ypos[i];
                d3 = this.spiderRobotInfo.realposz[i] - this.spiderRobotInfo.foot_zpos[i];
                dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                dd *= 16.0f;
            }
            float c1 = (float)(99.0 * Math.cos(this.spiderRobotInfo.p2xangle[i] - this.spiderRobotInfo.p1xangle[i]));
            float c2 = 99.0f;
            float c3 = (float)(99.0 * Math.cos(this.spiderRobotInfo.p2xangle[i] - this.spiderRobotInfo.p3xangle[i]));
            float cc = c1 + c2 + c3;
            float diff = cc - dd;
            this.spiderRobotInfo.pxvelocity[i] = this.getNewVelocity(realv, (float)((double)diff * Math.PI / 360.0), this.spiderRobotInfo.pxvelocity[i]);
            if (this.spiderRobotInfo.pxvelocity[i] == 0.0f || Math.abs(diff) < 8.0f) {
                ++fcount;
            }
            int n2 = i;
            this.spiderRobotInfo.p1xangle[n2] = this.spiderRobotInfo.p1xangle[n2] + (double)this.spiderRobotInfo.pxvelocity[i];
            this.spiderRobotInfo.p2xangle[i] = 0.0;
            this.spiderRobotInfo.p3xangle[i] = -this.spiderRobotInfo.p1xangle[i];
            dd = this.spiderRobotInfo.uppoint[i] != 0.0f ? (float)Math.atan2(dd, (double)(this.spiderRobotInfo.realposy[i] - this.spiderRobotInfo.uppoint[i]) * 16.0) : (float)Math.atan2(dd, (double)(this.spiderRobotInfo.realposy[i] - this.spiderRobotInfo.foot_ypos[i]) * 16.0);
            this.spiderRobotInfo.udwantedangle[i] = (float)((double)dd - 1.5707963267948966);
            while ((double)this.spiderRobotInfo.udwantedangle[i] > Math.PI) {
                int n3 = i;
                this.spiderRobotInfo.udwantedangle[n3] = (float)((double)this.spiderRobotInfo.udwantedangle[n3] - Math.PI * 2);
            }
            while ((double)this.spiderRobotInfo.udwantedangle[i] < -Math.PI) {
                int n4 = i;
                this.spiderRobotInfo.udwantedangle[n4] = (float)((double)this.spiderRobotInfo.udwantedangle[n4] + Math.PI * 2);
            }
            double rhm = this.spiderRobotInfo.udwantedangle[i];
            double rhdir = this.spiderRobotInfo.udcurrentangle[i];
            for (rdv = (rhm - rhdir) % (Math.PI * 2); rdv > Math.PI; rdv -= Math.PI * 2) {
            }
            while (rdv < -Math.PI) {
                rdv += Math.PI * 2;
            }
            diff = (float)rdv;
            this.spiderRobotInfo.udvelocity[i] = this.getNewVelocity(realv * 2.0f, diff, this.spiderRobotInfo.udvelocity[i]);
            if (this.spiderRobotInfo.udvelocity[i] == 0.0f || (double)Math.abs(diff) < Math.PI / 90) {
                this.spiderRobotInfo.uppoint[i] = 0.0f;
                ++fcount;
            }
            rhdir += (double)this.spiderRobotInfo.udvelocity[i];
            while (rhdir > Math.PI) {
                rhdir -= Math.PI * 2;
            }
            while (rhdir < -Math.PI) {
                rhdir += Math.PI * 2;
            }
            this.spiderRobotInfo.uddisplayangle[i] = dd = (this.spiderRobotInfo.udcurrentangle[i] = (float)rhdir);
            d3 = this.spiderRobotInfo.realposz[i] - this.spiderRobotInfo.foot_zpos[i];
            d1 = this.spiderRobotInfo.realposx[i] - this.spiderRobotInfo.foot_xpos[i];
            dd = (float)Math.atan2(d3, d1);
            this.spiderRobotInfo.ywantedangle[i] = dd;
            rhm = this.spiderRobotInfo.ywantedangle[i];
            rdv = (rhm - (rhdir = (double)this.spiderRobotInfo.ycurrentangle[i])) % (Math.PI * 2);
            if (rdv > Math.PI) {
                rdv -= Math.PI * 2;
            }
            if (rdv < -Math.PI) {
                rdv += Math.PI * 2;
            }
            diff = (float)rdv;
            this.spiderRobotInfo.yvelocity[i] = this.getNewVelocity(realv, diff, this.spiderRobotInfo.yvelocity[i]);
            if (this.spiderRobotInfo.yvelocity[i] == 0.0f || (double)Math.abs(diff) < Math.PI / 90) {
                ++fcount;
            }
            int n5 = i;
            this.spiderRobotInfo.ycurrentangle[n5] = this.spiderRobotInfo.ycurrentangle[n5] + this.spiderRobotInfo.yvelocity[i];
            while ((double)this.spiderRobotInfo.ycurrentangle[i] > Math.PI) {
                int n6 = i;
                this.spiderRobotInfo.ycurrentangle[n6] = (float)((double)this.spiderRobotInfo.ycurrentangle[n6] - Math.PI * 2);
            }
            while ((double)this.spiderRobotInfo.ycurrentangle[i] < -Math.PI) {
                int n7 = i;
                this.spiderRobotInfo.ycurrentangle[n7] = (float)((double)this.spiderRobotInfo.ycurrentangle[n7] + Math.PI * 2);
            }
            dd = (float)((double)this.spiderRobotInfo.ycurrentangle[i] - Math.toRadians(MathHelper.wrapDegrees((double)this.rotationYaw)) - 1.5707963267948966);
            while ((double)dd > Math.PI) {
                dd = (float)((double)dd - Math.PI * 2);
            }
            while ((double)dd < -Math.PI) {
                dd = (float)((double)dd + Math.PI * 2);
            }
            this.spiderRobotInfo.ydisplayangle[i] = dd;
            if (fcount != 3) continue;
            this.spiderRobotInfo.footup[i] = 0;

        }
    }

    private void findNewFooting(int i) {
        float dd;
        float d2;
        float fy;
        float fz;
        float fx;
        float f = 16.0f;
        boolean found = false;
        float range = 0.0f;
        double rhdir = Math.toRadians((this.rotationYaw + 90.0f) % 360.0f);
        double pi = 3.1415926545;
        this.spiderRobotInfo.footingticker[i] = 0;
        float d1 = (float)(this.getPosX() - this.prevPosX);
        float d3 = (float)(this.getPosZ() - this.prevPosZ);
        double rhm = Math.atan2(d3, d1);
        double velocity = Math.sqrt(d1 * d1 + d3 * d3);
        double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
        if (rdv > pi) {
            rdv -= pi * 2.0;
        }
        rdv = Math.abs(rdv);
        if (Math.abs(velocity) < 0.01) {
            rdv = 0.0;
        }
        range = this.spiderRobotInfo.yrange[i];
        range *= 0.875f;
        if (Math.abs((this.prevRotationYaw - this.rotationYaw) % 360.0f) > 0.75f) {
            range = 0.0f;
        }
        if (i >= 4) {
            f = 10.0f;
        }
        if (rdv > 1.5) {
            range = -range;
            f = 10.0f;
            if (i >= 4) {
                f = 16.0f;
            }
        }
        float deffx = fx = (float)((double)this.spiderRobotInfo.realposx[i] - (double)(f / 2.0f) * Math.sin(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.spiderRobotInfo.ymid[i]));
        float deffz = fz = (float)((double)this.spiderRobotInfo.realposz[i] + (double)(f / 2.0f) * Math.cos(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.spiderRobotInfo.ymid[i]));
        float deffy = fy = this.spiderRobotInfo.realposy[i] - 1.0f;
        float oldf = f;
        int span = 1;
        while (!found && f > 3.5f) {
            fx = (float)((double)this.spiderRobotInfo.realposx[i] - (double)f * Math.sin(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.spiderRobotInfo.ymid[i] - (double)range));
            fz = (float)((double)this.spiderRobotInfo.realposz[i] + (double)f * Math.cos(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.spiderRobotInfo.ymid[i] - (double)range));
            fy = this.spiderRobotInfo.realposy[i];
            for (int j = 11; !found && j > -14; --j) {
                block2: for (int m = -span; !found && m <= span; ++m) {
                    for (int n = -span; !found && n <= span; ++n) {
                        BlockState blk = this.world.getBlockState(new BlockPos(MathHelper.floor(fx) + m, MathHelper.floor(fy) + j, MathHelper.floor(fz) + n));
                        if (blk.isAir() || !this.world.getBlockState(new BlockPos(MathHelper.floor(fx) + m, MathHelper.floor(fy) + j, MathHelper.floor(fz) + n)).getMaterial().isSolid()) continue;
                        fy += (float)(j + 1);
                        fx += (float)m;
                        fz += (float)n;
                        found = true;
                        continue block2;
                    }
                }
            }
            if (found) {
                d1 = this.spiderRobotInfo.realposx[i] - fx;
                d2 = this.spiderRobotInfo.realposy[i] - fy;
                d3 = this.spiderRobotInfo.realposz[i] - fz;
                dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                if ((dd *= 16.0f) > 294.0f) {
                    found = false;
                }
            }
            if (!((f -= 1.0f) < 3.5f) || range == 0.0f) continue;
            range = 0.0f;
            span = 3;
            f = oldf;
        }
        if (!found) {
            fx = deffx;
            fy = deffy;
            fz = deffz;
        }
        float sfx = this.spiderRobotInfo.foot_xpos[i];
        float sfy = this.spiderRobotInfo.foot_ypos[i];
        float sfz = this.spiderRobotInfo.foot_zpos[i];
        this.spiderRobotInfo.foot_xpos[i] = fx;
        this.spiderRobotInfo.foot_ypos[i] = fy;
        this.spiderRobotInfo.foot_zpos[i] = fz;
        if (this.spiderRobotInfo.footup[i] == 0) {
            this.spiderRobotInfo.footup[i] = 1;
            d1 = sfx - fx;
            d2 = sfy - fy;
            d3 = sfz - fz;
            dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            dd *= 16.0f;
            d1 = (sfy + fy) / 2.0f;
            if (dd > 3.0f) {
                d1 += 1.0f;
            }
            if (dd > 48.0f) {
                d1 += 1.5f;
            }
            if (dd > 100.0f) {
                d1 += 1.5f;
            }
            this.spiderRobotInfo.uppoint[i] = d1;
        }
    }

    }
}
